package com.example.namanassignment.util

import com.example.namanassignment.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newUrl = request.url.newBuilder().addQueryParameter(API_KEY_PARAMETER, API_KEY).build()
        val newRequest = request.newBuilder().url(newUrl).build()
        return chain.proceed(newRequest)
    }

    companion object {
        const val API_KEY_PARAMETER = "api_key"
        const val API_KEY = BuildConfig.API_KEY
    }
}