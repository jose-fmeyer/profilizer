package com.profilizer

import android.app.Application
import android.content.Context
import android.support.annotation.VisibleForTesting
import com.profilizer.common.di.component.ApplicationComponent

class ProfilizerApplication : Application() {

    companion object {
        lateinit var applicationComponent : ApplicationComponent

        fun get(context: Context): ProfilizerApplication {
            return context.applicationContext as ProfilizerApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        applicationComponent = ApplicationComponent.init(this)
    }

    @VisibleForTesting
    fun setComponent(component: ApplicationComponent) {
        applicationComponent = component
        component.inject(this)
    }
}