package com.sam.weatherapp.network

import com.sam.weatherapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AppInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url.newBuilder()
            .addQueryParameter("appid", BuildConfig.APP_ID)
            .build()

        val request = chain.request().newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}
