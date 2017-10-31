package com.profilizer.common.component

import android.content.Context
import android.content.res.Resources
import com.profilizer.ProfilizerApplication
import com.profilizer.activities.LatestTestsActivity
import com.profilizer.common.module.AppModule
import com.profilizer.common.module.NetworkModule
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
}