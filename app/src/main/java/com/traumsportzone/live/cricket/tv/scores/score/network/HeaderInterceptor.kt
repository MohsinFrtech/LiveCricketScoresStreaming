package com.traumsportzone.live.cricket.tv.scores.score.network

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("appid", "hello")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                /*.removeHeader("User-Agent")
                .addHeader(
                    "User-Agent",
                    "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:38.0) Gecko/20100101 Firefox/38.0"
                )*/
                .build()
        )
    }
}