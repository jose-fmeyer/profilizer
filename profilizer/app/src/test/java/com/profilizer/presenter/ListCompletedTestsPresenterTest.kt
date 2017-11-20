package com.profilizer.presenter

import android.content.Context
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.profilizer.common.util.NetworkUtil
import com.profilizer.common.util.RxAndroidTestRule
import com.profilizer.common.util.TestUtils
import com.profilizer.personalitytest.contracts.ListCompletedTestsContract
import com.profilizer.personalitytest.model.Answer
import com.profilizer.personalitytest.presenter.ListCompletedTestsPresenterImpl
import com.profilizer.personalitytest.services.AnswerService
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)
@PrepareForTest(NetworkUtil::class)
class ListCompletedTestsPresenterTest {

    @Rule val rxAndroidTestRule = RxAndroidTestRule()
    @Mock lateinit var service: AnswerService
    @Mock lateinit var view: ListCompletedTestsContract.View
    @Mock lateinit var context: Context
    private lateinit var presenter: ListCompletedTestsContract.Presenter
    private lateinit var answers : List<Answer>
    private val testUtils = TestUtils()

    @Before
    fun setUp() {
        presenter = ListCompletedTestsPresenterImpl(view, service)
        presenter.onAttach()
        answers = listOf(testUtils.mockAnswer())
        PowerMockito.mockStatic(NetworkUtil::class.java)
    }

    @After
    fun onFinish() {
        presenter.onDetach()
    }

    @Test
    fun testAnswers() {
        given(NetworkUtil.isNotConnected(any())).willReturn(false)
        given(service.loadCompletedTestAnswers(any())).willReturn(Single.just(answers))

        presenter.loadCompletedTestData(TestUtils.TEST_ID)

        verify(view, times(1)).showCompletedTestData(any())
    }

    @Test
    fun testStartLoading() {
        given(NetworkUtil.isNotConnected(any())).willReturn(false)
        given(service.loadCompletedTestAnswers(any())).willReturn(Single.just(answers))

        presenter.loadCompletedTestData(TestUtils.TEST_ID)

        verify(view, times(1)).onStartLoading()
    }

    @Test
    fun testShowErrorMessage() {
        given(NetworkUtil.isNotConnected(any())).willReturn(false)
        given(service.loadCompletedTestAnswers(any())).willReturn(Single.error(Throwable()))

        presenter.loadCompletedTestData(TestUtils.TEST_ID)

        verify(view, times(1)).showErrorMessage()
    }

    @Test
    fun testShowNoNetworkConnectionMessage() {
        given(view.getViewContext()).willReturn(context)
        given(NetworkUtil.isNotConnected(any())).willReturn(true)

        presenter.loadCompletedTestData(TestUtils.TEST_ID)

        verify(view, times(1)).showNoNetworkMessage()
    }
}