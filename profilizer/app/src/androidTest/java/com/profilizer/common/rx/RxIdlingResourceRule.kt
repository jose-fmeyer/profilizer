package com.profilizer.common.rx

import android.support.test.espresso.Espresso

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class RxIdlingResourceRule : TestRule {

    private var mIdlingResource: RxIdlingResource = RxIdlingResource()

    override fun apply(base: Statement, description: Description): Statement {

        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                set()
                base.evaluate()
                reset()
            }
        }
    }

    private fun set() {
        Espresso.registerIdlingResources(mIdlingResource)
    }

    private fun reset() {
        Espresso.unregisterIdlingResources(mIdlingResource)
    }
}
