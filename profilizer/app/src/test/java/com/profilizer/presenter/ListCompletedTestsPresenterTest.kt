package com.profilizer.presenter

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.profilizer.personalitytest.contracts.LatestTestsContract
import com.profilizer.personalitytest.contracts.ListCompletedTestsContract
import com.profilizer.personalitytest.model.Answer
import com.profilizer.personalitytest.model.PersonalityTest
import com.profilizer.personalitytest.presenter.LatestTestsPresenterImpl
import com.profilizer.personalitytest.presenter.ListCompletedTestsPresenterImpl
import com.profilizer.personalitytest.services.AnswerService
import com.profilizer.personalitytest.services.PersonalityTestService
import com.profilizer.presenter.util.TestUtils
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito
import org.mockito.Mock

class ListCompletedTestsPresenterTest {

    @Mock lateinit var service: AnswerService
    @Mock lateinit var view: ListCompletedTestsContract.View
    private lateinit var presenter: ListCompletedTestsContract.Presenter
    private lateinit var answers : List<Answer>
    private val testUtils = TestUtils()

    @Before
    fun setUp() {
        presenter = ListCompletedTestsPresenterImpl(view, service)
        presenter.onAttach()
        answers = listOf(testUtils.mockAnswer())
    }

    @After
    fun onFinish() {
        presenter.onDetach()
    }

    @Test
    fun testAnswers() {
        BDDMockito.given(service.loadAnswers()).willReturn(Observable.just(answers))

        presenter.loadCompletedTestData(TestUtils.TEST_ID)

        verify(view, times(1)).showCompletedTestData(any())
    }

    @Test
    fun testStartLoading() {
        BDDMockito.given(service.loadAnswers()).willReturn(Observable.just(answers))

        presenter.loadCompletedTestData(TestUtils.TEST_ID)

        verify(view, times(1)).onStartLoading()
    }

    @Test
    fun testShowErrorMessage() {
        BDDMockito.given(service.loadAnswers()).willReturn(Observable.error(Throwable()))

        presenter.loadCompletedTestData(TestUtils.TEST_ID)

        verify(view, times(1)).showErrorMessage()
    }
}