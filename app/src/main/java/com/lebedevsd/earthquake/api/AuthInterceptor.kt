package com.lebedevsd.earthquake.api

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor
@Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val oldUrl = chain.request().url()
        val newRequest = chain.request().newBuilder()
            .url(oldUrl.newBuilder().addQueryParameter(USERNAME, USERNAME_VALUE).build())
            .build()
        return chain.proceed(newRequest)
    }

    companion object{
        private const val USERNAME = "username"
        private const val USERNAME_VALUE = "mkoppelman"
    }
}
