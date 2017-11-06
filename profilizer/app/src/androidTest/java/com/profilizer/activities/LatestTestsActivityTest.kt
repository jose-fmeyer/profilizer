package com.profilizer.activities

import android.content.Intent
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.profilizer.R
import com.profilizer.personalitytest.adapter.LatestTestsDelegateAdapter.LatestTestsViewHolder
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LatestTestsActivityTest {

    @get:Rule
    var activityRule = ActivityTestRule(LatestTestsActivity::class.java, false, false)

    @Before
    fun setup() {
        activityRule.launchActivity(Intent())
    }

    @Test
    fun testUserSeeTheLastTests() {
        Espresso.onView(ViewMatchers.withId(R.id.latest_container))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.tests_list))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testUserClickTest() {
        Espresso.onView(ViewMatchers.withId(R.id.latest_container))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.tests_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition<LatestTestsViewHolder>(0,
                        ViewActions.click()))
    }
}