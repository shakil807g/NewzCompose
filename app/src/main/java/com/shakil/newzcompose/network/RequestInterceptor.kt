package com.shakil.newzcompose.network

import com.shakil.newzcompose.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder().url(originalRequest.url).build()
        return chain.proceed(request)
    }
}

class NewsApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestWithApiKey = chain.request().newBuilder()
            .header("X-Api-Key", BuildConfig.API_KEY)
            .build()
        return chain.proceed(requestWithApiKey)
    }
}