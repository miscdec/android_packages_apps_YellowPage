package org.exthmui.yellowpage.lookup.flyme.bean

import android.text.TextUtils
import kotlinx.serialization.Serializable

@Serializable
data class PhoneNumberResponse(
    var code: Int,
    var message: String? = null,
    var redirect: String? = null,
    var value: DistPhoneResponse? = null,
) {
    override fun toString(): String {
        return "BaseEntry{code=${this.code}, message='${this.message}', redirect='${this.redirect}', value='${this.value}'}"
    }
}

@Serializable
data class DistPhoneResponse(
    var amount: Int = 0,
    var cp: String? = null,
    var cpInfo: String? = null,
    var icon: String? = null,
    var id: Int = 0,
    var isNetHaveIcon: Boolean = false,
    var name: String? = null,
    var names: String? = null,
    var number: String? = null,
    var rating: Int? = null,
    var tag: String? = null,
    var time: String? = null,
    var type: Int = 0,
    val isNull: Boolean = TextUtils.isEmpty(icon) && TextUtils.isEmpty(tag)
) {
    override fun toString(): String {
        return "DistPhoneResponse{icon='" + this.icon + "', tag='" + this.tag + "', rating=" + this.rating + ", amount=" + this.amount + ", cp='" + this.cp + "', name='" + this.name + "', time='" + this.time + "', names='" + this.names + "', cpInfo='" + this.cpInfo + "', id=" + this.id + ", number='" + this.number + "', isNetHasIcon=" + this.isNetHaveIcon + ", type=" + this.type + '}'
    }
}

