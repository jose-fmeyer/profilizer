package com.profilizer.common.module

import com.profilizer.BuildConfig.BASE_END_POINT
import com.profilizer.common.api.ApiAuthenticationInterceptor
import com.profilizer.common.api.DateJsonAdpater
import com.squareup.moshi.Moshi
import com.squareup.moshi.Rfc3339DateJsonAdapter
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
                .add(Date::class.java, DateJsonAdpater())
                .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(ApiAuthenticationInterceptor())
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi, client : OkHttpClient) : Retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_END_POINT)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
}