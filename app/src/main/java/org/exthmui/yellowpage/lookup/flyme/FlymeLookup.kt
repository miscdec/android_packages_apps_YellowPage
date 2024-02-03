package org.exthmui.yellowpage.lookup.flyme

import android.content.Context
import android.text.TextUtils
import android.util.Log
import kotlinx.coroutines.runBlocking
import org.exthmui.yellowpage.R
import org.exthmui.yellowpage.lookup.PhoneNumberLookup
import org.exthmui.yellowpage.lookup.flyme.bean.DistPhoneResponse
import org.exthmui.yellowpage.lookup.flyme.bean.PhoneNumberResponse
import org.exthmui.yellowpage.misc.Constants
import org.exthmui.yellowpage.models.PhoneNumberInfo


class FlymeLookup : PhoneNumberLookup {
    override fun lookup(context: Context, number: String): PhoneNumberInfo {

        var phoneNumberInfo = PhoneNumberInfo(
            number = number,
            type = Constants.PhoneNumberTagData.TYPE_NORMAL,
            tag = null
        )

        runBlocking {
            val result = queryPhoneNumber(context, number)
            result.value?.let {
                if (!it.name.isNullOrEmpty()) {
                    phoneNumberInfo = phoneNumberInfo.copy(
                        type = Constants.PhoneNumberTagData.TYPE_SERVICE,
                        tag = it.name
                    )
                }
                if (!it.tag.isNullOrEmpty()) {
                    getTypeByTag(context, it).apply {
                        phoneNumberInfo = phoneNumberInfo.copy(
                            tag = this.first,
                            type = this.second
                        )
                    }
                    Log.d("getTypeByTag", "FlymeLookup.lookup() result = $phoneNumberInfo")
                }
            }

        }

        return phoneNumberInfo
    }


    fun getTypeByTag(context: Context, info: DistPhoneResponse): Pair<String?, Int> {

        if (!info.tag.isNullOrEmpty()) {
            val spamEntries = context.resources.getStringArray(R.array.block_tag_entries)
            for (i in spamEntries.indices) {
                if (info.tag!!.contains(spamEntries[i])) {
                    return Pair("${info.cp}:有${info.amount}人将其标记为${info.tag!!}", -i - 1)
                }
            }
        }
        if (info.name.isNullOrEmpty()) {
            return Pair(info.name!!, Constants.PhoneNumberTagData.TYPE_SERVICE)
        }
        return Pair(null, Constants.PhoneNumberTagData.TYPE_NORMAL)
    }

    private suspend fun queryPhoneNumber(context: Context, number: String): PhoneNumberResponse {
        val str2 = "1"
        val PUSH_TYPE_NOTIFY = "0"
        val UXIP_REQUEST_PARAM_NONCE = "nonce"
        val i = 0
        val str3 = ""
        val params = Signer(context)
            .append("key", "H10ZTcsVrNFNzdvzEIFdvtdJReaMu6HopqqPMnezOaSmPzGJkvtGhaDQX1982o2l")
            .append("number", number)
            .append("scene", str2.ifEmpty { PUSH_TYPE_NOTIFY })
            .append(UXIP_REQUEST_PARAM_NONCE, Signer.a.a())
            .a("isSms", i.toString())
            .a("smsSign", str3)
            .a("iccid", Util.getfakeIccid())
            .a("cityCode", "")
            .b()

        Log.d(
            "OkHttpFactory",
            """ call ${UrlConstant.Url.url_phone_number_tag_add} text = $params"""
        )

        val result = flymeApi.query(FlymeRepository.urlEncode(params, "UTF-8"))

        Log.d("OKresult", result.toString())
        return result


    }

    /**
     * 检查这个查询接口是否支持该地区电话号码
     * @param code 国际区号
     * @return
     */
    override fun checkRegion(code: Long): Boolean {
        return code == 86L
    }
}
