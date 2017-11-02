package com.profilizer.presenter

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.profilizer.personalitytest.contracts.StartPersonalityTestTestContract
import com.profilizer.personalitytest.model.PersonalityTestQuestions
import com.profilizer.personalitytest.presenter.StartPersonalityTestPresenterImpl
import com.profilizer.personalitytest.services.PersonalityTestService
import com.profilizer.presenter.util.TestUtils
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito
import org.mockito.Mock

class StartPersonalityPresenterTest {

    @Mock lateinit var service: PersonalityTestService
    @Mock lateinit var view: StartPersonalityTestTestContract.View
    private lateinit var presenter: StartPersonalityTestTestContract.Presenter
    private lateinit var testQuestions : PersonalityTestQuestions
    private val testUtils = TestUtils()

    @Before
    fun setUp() {
        presenter = StartPersonalityTestPresenterImpl(view, service)
        presenter.onAttach()
        testQuestions = testUtils.mockQuestionTestQuestions()
    }

    @After
    fun onFinish() {
        presenter.onDetach()
    }

    @Test
    fun testLoadTestQuestions() {
        BDDMockito.given(service.loadTestsQuestions()).willReturn(Observable.just(testQuestions))

        presenter.loadTestQuestions()

        verify(view, times(1)).onFinishLoadingTestQuestions(any())
    }

    @Test
    fun testStartLoading() {
        BDDMockito.given(service.loadTestsQuestions()).willReturn(Observable.just(testQuestions))

        presenter.loadTestQuestions()

        verify(view, times(1)).startLoading()
    }

    @Test
    fun testShowErrorMessage() {
        BDDMockito.given(service.loadTestsQuestions()).willReturn(Observable.error(Throwable()))

        presenter.loadTestQuestions()

        verify(view, times(1)).showErrorMessage()
    }
}