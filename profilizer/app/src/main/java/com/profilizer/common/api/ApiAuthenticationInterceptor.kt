package com.profilizer.common.api

import com.profilizer.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiAuthenticationInterceptor : Interceptor {

    companion object {
        val AUTHORIZATION_PARAM = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = with(chain.request()) {
            newBuilder()
                .addHeader(AUTHORIZATION_PARAM, "Basic ${BuildConfig.API_AUTH}")
                .build()
        }
        return chain.proceed(request)
    }
}