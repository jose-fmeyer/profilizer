package com.profilizer.common.rx

import android.support.test.espresso.IdlingResource
import android.support.test.espresso.IdlingResource.ResourceCallback
import java.util.concurrent.locks.ReentrantLock
import java.util.function.Function


class RxIdlingResource : IdlingResource, Function<Runnable, Runnable> {

    // Guarded by idlingStateLock
    private var taskCount = 0

    // Guarded by idlingStateLock
    private var transitionCallback: ResourceCallback? = null

    override fun getName(): String {
        return RESOURCE_NAME
    }

    override fun isIdleNow(): Boolean {
        val result: Boolean

        idlingStateLock.lock()
        result = taskCount == 0
        idlingStateLock.unlock()

        return result
    }

    override fun registerIdleTransitionCallback(callback: ResourceCallback) {
        idlingStateLock.lock()
        this.transitionCallback = callback
        idlingStateLock.unlock()
    }

    @Throws(Exception::class)
    override fun apply(runnable: Runnable): Runnable {
        return Runnable {
            idlingStateLock.lock()
            taskCount++
            idlingStateLock.unlock()

            try {
                runnable.run()
            } finally {
                idlingStateLock.lock()

                try {
                    taskCount--

                    if (taskCount == 0 && transitionCallback != null) {
                        transitionCallback!!.onTransitionToIdle()
                    }
                } finally {
                    idlingStateLock.unlock()
                }
            }
        }
    }

    companion object {

        private val RESOURCE_NAME = RxIdlingResource::class.java.simpleName

        private val idlingStateLock = ReentrantLock()
    }
}