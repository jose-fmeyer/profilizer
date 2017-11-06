package com.profilizer.common.di.component

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.profilizer.ProfilizerApplication
import com.profilizer.common.di.module.AppModule
import com.profilizer.common.di.module.NetworkModule
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, NetworkModule::class))
interface ApplicationComponent {
    fun getRetrofit(): Retrofit
    fun getResources(): Resources
    fun getContext(): Context
    fun inject(application: ProfilizerApplication)

    companion object {

        fun init(application: Application): ApplicationComponent {

            return DaggerApplicationComponent.builder()
                    .appModule(AppModule(application))
                    .networkModule(NetworkModule())
                    .build()
        }
    }
}