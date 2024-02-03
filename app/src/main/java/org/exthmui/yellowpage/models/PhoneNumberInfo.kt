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
package org.exthmui.yellowpage.models

import android.content.Context
import android.text.TextUtils
import org.exthmui.yellowpage.R
import org.exthmui.yellowpage.misc.Constants

data class PhoneNumberInfo(
    @JvmField
    var number: String,
    @JvmField
    var type: Int = Constants.PhoneNumberTagData.TYPE_NORMAL,
    @JvmField
    var tag: String? = null,
) {

    companion object {
        fun getTypeByTag(context: Context, info: PhoneNumberInfo): PhoneNumberInfo {
            when {
                TextUtils.isEmpty(info.tag) -> {
                    return info.copy(type = Constants.PhoneNumberTagData.TYPE_NORMAL)
                }

                else -> {
                    val spamEntries = context.resources.getStringArray(R.array.block_tag_entries)
                    for (i in spamEntries.indices) {
                        if (info.tag!!.contains(spamEntries[i])) {
                            return info.copy(type = -i - 1)
                        }
                    }
                }
            }
            return info.copy(type = Constants.PhoneNumberTagData.TYPE_SERVICE)
        }
    }
}
