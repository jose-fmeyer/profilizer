package com.profilizer

import android.app.Application
import com.profilizer.common.component.ApplicationComponent
import com.profilizer.common.component.DaggerApplicationComponent
import com.profilizer.common.module.AppModule
import com.profilizer.common.module.NetworkModule

class ProfilizerApplication : Application() {

    companion object {
        lateinit var applicationComponent : ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder()
                .appModule(AppModule(this))
                .networkModule(NetworkModule())
                .build()
    }
}