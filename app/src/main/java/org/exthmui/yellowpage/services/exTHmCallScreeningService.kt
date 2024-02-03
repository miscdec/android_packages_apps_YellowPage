/*
 * Copyright (C) 2020 The exTHmUI Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.exthmui.yellowpage.services

import android.content.SharedPreferences
import android.telecom.Call
import android.telecom.CallScreeningService
import androidx.preference.PreferenceManager
import org.exthmui.yellowpage.R
import org.exthmui.yellowpage.helpers.PhoneNumberTagDbHelper
import org.exthmui.yellowpage.lookup.PhoneNumberLookup
import org.exthmui.yellowpage.lookup.flyme.FlymeLookup
import org.exthmui.yellowpage.misc.Constants
import org.exthmui.yellowpage.models.PhoneNumberInfo
import java.io.UnsupportedEncodingException
import java.net.URLDecoder

class exTHmCallScreeningService : CallScreeningService() {
    private var phoneNumberTagDbHelper: PhoneNumberTagDbHelper? = null
    private val phoneNumberLookups = arrayOf<PhoneNumberLookup>(FlymeLookup())
    private var sharedPreferences: SharedPreferences? = null
    private val mRecentRejectPhoneNumber: MutableMap<String, Long> = HashMap()

    private val REPEAT_LIMIT_TIME = (60 * 20 * 1000).toLong()// 20 minutes

    override fun onCreate() {
        super.onCreate()
        phoneNumberTagDbHelper = PhoneNumberTagDbHelper(this)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
    }

    private fun lookupInfoOnline(number: String, countryCode: Long): PhoneNumberInfo? {
        var info: PhoneNumberInfo? = null
        for (phoneNumberLookup in phoneNumberLookups) {
            if (countryCode == 0L || phoneNumberLookup.checkRegion(countryCode)) {
                info = phoneNumberLookup.lookup(this, number)
                if (info.type != Constants.PhoneNumberTagData.TYPE_NORMAL) {
                    break
                }
            }
        }
        return info
    }

    private fun shouldBlock(info: PhoneNumberInfo): Boolean {
        if (sharedPreferences!!.getBoolean(Constants.KEY_CALLER_ID_NO_BLOCK_REPEAT, true)) {
            val currentTimeStamp = System.currentTimeMillis()
            if (currentTimeStamp - mRecentRejectPhoneNumber.getOrDefault(
                    info.number,
                    0L
                ) <= REPEAT_LIMIT_TIME
            ) {
                mRecentRejectPhoneNumber[info.number] = currentTimeStamp
                return false
            }
            mRecentRejectPhoneNumber[info.number] = currentTimeStamp
        }
        if (info.type < 0) {
            if (sharedPreferences!!.getBoolean(Constants.KEY_CALLER_ID_BLOCK_BY_TAG, false)) {
                val needRejects =
                    sharedPreferences!!.getStringSet(Constants.KEY_CALLER_ID_BLOCK_TAGS, null)
                val spamValues = resources.getStringArray(R.array.block_tag_values)
                if (needRejects == null) return false
                return spamValues.size >= -info.type && needRejects.contains(spamValues[-info.type - 1])
            } else {
                return true
            }
        }
        return false
    }

    override fun onScreenCall(callDetails: Call.Details) {
        if (!sharedPreferences!!.getBoolean(Constants.KEY_CALLER_ID_ENABLED, true)) return
        var number = callDetails.handle.toString().substring(4)
        val countryCode = getCountryCode(number)

        try {
            number = URLDecoder.decode(number, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        number = number.replace("-".toRegex(), "").replace(" ".toRegex(), "")
        if (number.startsWith("+")) {
            number = number.substring(1)
        }
        val builder = CallResponse.Builder()
        val finalNumber = number
        Thread {
            var info: PhoneNumberInfo? = null
            try {
                info = phoneNumberTagDbHelper!!.query(finalNumber)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if (info == null) {
                info = lookupInfoOnline(finalNumber, countryCode)
                if (info!!.type != Constants.PhoneNumberTagData.TYPE_NORMAL) {
                    phoneNumberTagDbHelper!!.insertData(finalNumber, info.tag, info.type)
                }
            }
            /*
                    builder.setDisallowCall(false)      // 阻止来电传入
                            .setRejectCall(false)       // 拒接来电 （仅当 disallowCall 时有效）
                            .setSkipNotification(false) // 不显示未接来电通知（仅当 disallowCall 时有效）
                            .setSkipCallLog(false)      // 阻止到达通话记录 （仅当 disallowCall 时有效）
                            .setSilenceCall(false);     // 不响铃 （仅当 disallowCall 为 false 时有效）
                     */
            if (shouldBlock(info)) {
                builder.setDisallowCall(true)
                builder.setRejectCall(true)
                builder.setSkipNotification(true)
            }
            respondToCall(callDetails, builder.build())
        }.start()
    }

    private fun getCountryCode(number: String): Long {
        if (number[0] == '+') {
            val countryCodes = resources.getStringArray(R.array.country_codes)
            for (code in countryCodes) {
                if (number.startsWith("+$code")) {
                    return code.toLong()
                }
            }
        }
        return 0
    }

    companion object {
        private const val TAG = "exTHmCallScreeningService"
    }
}
