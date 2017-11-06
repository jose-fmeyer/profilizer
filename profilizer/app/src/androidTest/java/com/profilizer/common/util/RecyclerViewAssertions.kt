package com.profilizer.common.util

import android.support.v7.widget.RecyclerView
import android.support.test.espresso.ViewAssertion
import org.junit.Assert.assertEquals


object RecyclerViewAssertions {

    fun hasItemsCount(count: Int): ViewAssertion {
        return ViewAssertion { view, e ->
            if (view !is RecyclerView) {
                throw e
            }
            assertEquals(view.adapter.itemCount, count)
        }
    }
}