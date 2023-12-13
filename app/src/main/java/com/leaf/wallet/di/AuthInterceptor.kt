package com.leaf.wallet.di

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Singleton

@Singleton
class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val request = requestBuilder
            .addHeader("apikey", "1yJg56aYgDPSAwO5mMhmq7I8AMxje8Zs")
            .header("Cache-Control", "public, max-age=86400")
            .build()
        return chain.proceed(request)
    }
}