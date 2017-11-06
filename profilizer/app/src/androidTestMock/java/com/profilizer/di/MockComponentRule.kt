package com.profilizer.di

import android.content.Context

import com.profilizer.ProfilizerApplication
import com.profilizer.common.di.component.ApplicationComponent

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

import java.io.IOException

import okhttp3.mockwebserver.MockWebServer

class MockComponentRule(private val context: Context) : TestRule {

    private lateinit var applicationComponent: ApplicationComponent
    private lateinit var mockWebServer: MockWebServer

    override fun apply(base: Statement, description: Description): Statement {

        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                val application = ProfilizerApplication.get(context)
                val endpoint = initMockWebServer()

                val applicationComponent = ProfilizerApplication.applicationComponent
                setupInjection(application, endpoint)

                base.evaluate()

                application.setComponent(applicationComponent)
            }
        }
    }

    private fun setupInjection(application: ProfilizerApplication, endpoint: String) {
        this.applicationComponent = ApplicationComponent.init(application, endpoint);

        // set mock application component on application
        application.setComponent(applicationComponent)
    }

    private fun initMockWebServer(): String {
        mockWebServer = MockWebServer()
        try {
            mockWebServer.start()
        } catch (e: IOException) {
            e.printStackTrace()
            throw RuntimeException("Unable to start MockWebServer")
        }

        return mockWebServer.url("/").toString()
    }

    fun getMockWebServer(): MockWebServer {
        return mockWebServer
    }
}
