package org.exthmui.yellowpage.network.ktor


import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.exthmui.yellowpage.BuildConfig

val httpClient = HttpClient(OkHttp) {
    engine {
        // this: OkHttpConfig
        config {
            // this: OkHttpClient.Builder
            followRedirects(true)
            retryOnConnectionFailure(true)
        }
    }
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
    install(Logging) {
        if (BuildConfig.DEBUG) {
            level = LogLevel.ALL
            logger = Logger.SIMPLE
        }
    }

}

