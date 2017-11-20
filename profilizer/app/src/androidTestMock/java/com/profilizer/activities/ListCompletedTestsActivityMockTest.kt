package com.profilizer.activities

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import com.profilizer.R
import com.profilizer.common.rx.RxIdlingResourceRule
import com.profilizer.common.util.IOUtil
import com.profilizer.common.util.RecyclerViewAssertions
import com.profilizer.common.util.TestUtils
import com.profilizer.di.MockComponentRule
import okhttp3.mockwebserver.MockResponse
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import java.io.IOException
import java.net.HttpURLConnection

@RunWith(AndroidJUnit4::class)
class ListCompletedTestsActivityMockTest {

    private var activityTestRule = ActivityTestRule<ListCompletedTestsActivity>(ListCompletedTestsActivity::class.java, true, false)
    private var mockComponentRule = MockComponentRule(InstrumentationRegistry.getTargetContext())
    private var rxIdlingRule = RxIdlingResourceRule()

    @Rule
    @JvmField
    val chain: TestRule = RuleChain.outerRule(rxIdlingRule).around(mockComponentRule).around(activityTestRule)

    private fun enqueueResponse(status: Int, assetFilePath: String) {
        val mockWebServer = mockComponentRule.getMockWebServer()
        try {
            mockWebServer.enqueue(MockResponse()
                    .setResponseCode(status)
                    .setBody(IOUtil.readAsset(InstrumentationRegistry.getContext(), assetFilePath)))
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    @Test
    fun testOnResumeShowErrorMessageWhenLoadingAnswersFails() {
        enqueueResponse(HttpURLConnection.HTTP_NOT_FOUND, "response_404")

        val targetContext = InstrumentationRegistry.getInstrumentation()
                .targetContext

        activityTestRule.launchActivity(ListCompletedTestsActivity.buildCompletedTestIntent(
                targetContext, TestUtils.TEST_ID))

        onView(withId(R.id.progress_bar)).check(matches(Matchers.not<View>(isDisplayed())))

        onView(Matchers.allOf(withId(android.support.design.R.id.snackbar_text), withText(R.string.load_error_message)))
                .check(matches(isDisplayed()));
    }

    @Test
    fun testShowAnswersOnLoadTestsSucceed() {
        enqueueResponse(HttpURLConnection.HTTP_OK, "response_200_answers.json")

        val targetContext = InstrumentationRegistry.getInstrumentation()
                .targetContext

        activityTestRule.launchActivity(ListCompletedTestsActivity.buildCompletedTestIntent(
                targetContext, TestUtils.TEST_ID))

        onView(withId(R.id.progress_bar)).check(matches(Matchers.not<View>(isDisplayed())))

        onView(withId(R.id.answers_list)).check(RecyclerViewAssertions.hasItemsCount(3));
    }
}