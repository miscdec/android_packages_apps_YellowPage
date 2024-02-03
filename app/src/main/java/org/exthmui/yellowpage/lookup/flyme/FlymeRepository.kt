package org.exthmui.yellowpage.lookup.flyme


import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.Headers
import de.jensklingenberg.ktorfit.http.POST
import org.exthmui.yellowpage.lookup.flyme.bean.PhoneNumberResponse
import org.exthmui.yellowpage.network.ktor.httpClient
import java.io.UnsupportedEncodingException
import java.net.URLEncoder


interface FlymeRepository {


    @POST(UrlConstant.Url.url_phone_number_query)
    @Headers(
        "Accept: application/json",
        "Content-Type: application/x-www-form-urlencoded;charset=utf-8"
    )
    suspend fun query(
        @Body text: ByteArray
    ): PhoneNumberResponse

    companion object {

        fun urlEncode(map: Map<String, String>, str: String): ByteArray {
            val sb = StringBuilder()
            try {
                for ((key, value) in map) {
                    sb.append(URLEncoder.encode(key, str))
                    sb.append('=')
                    sb.append(URLEncoder.encode(value, str))
                    sb.append('&')
                }
                return sb.toString().toByteArray(charset(str))
            } catch (e: UnsupportedEncodingException) {
                throw RuntimeException("Encoding not supported: $str", e)
            }
        }

    }


}


val ktorfit = Ktorfit.Builder()
    .httpClient(httpClient)
    .baseUrl(UrlConstant.Url.base_url).build()
val flymeApi = ktorfit.create<FlymeRepository>()





