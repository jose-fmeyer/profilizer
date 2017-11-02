package com.profilizer.presenter

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.profilizer.common.CategoryType
import com.profilizer.personalitytest.contracts.QuestionContract
import com.profilizer.personalitytest.model.Answer
import com.profilizer.personalitytest.presenter.QuestionPresenterImpl
import com.profilizer.personalitytest.services.AnswerService
import com.profilizer.presenter.util.TestUtils
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito
import org.mockito.Mock

class QuestionPresenterTest {

    @Mock lateinit var service: AnswerService
    @Mock lateinit var view: QuestionContract.View
    private lateinit var presenter: QuestionContract.Presenter
    private lateinit var answer : Answer
    private val testUtils = TestUtils()

    @Before
    fun setUp() {
        presenter = QuestionPresenterImpl(view, service)
        presenter.onAttach()
        answer = testUtils.mockAnswer()
    }

    @After
    fun onFinish() {
        presenter.onDetach()
    }

    @Test
    fun testFinishSaveAnswer() {
        BDDMockito.given(service.createAnswer(any())).willReturn(Observable.just(answer))

        presenter.saveAnswer(TestUtils.ANSWER, TestUtils.QUESTION, CategoryType.INTROVERSION.category,
                TestUtils.TEST_ID)

        verify(view, times(1)).onFinishSaveAnswer(any())
    }

    @Test
    fun testStartLoading() {
        BDDMockito.given(service.createAnswer(any())).willReturn(Observable.just(answer))

        presenter.saveAnswer(TestUtils.ANSWER, TestUtils.QUESTION, CategoryType.INTROVERSION.category,
                TestUtils.TEST_ID)

        verify(view, times(1)).onStartLoading()
    }

    @Test
    fun testShowErrorMessage() {
        BDDMockito.given(service.createAnswer(any())).willReturn(Observable.error(Throwable()))

        presenter.saveAnswer(TestUtils.ANSWER, TestUtils.QUESTION, CategoryType.INTROVERSION.category,
                TestUtils.TEST_ID)

        verify(view, times(1)).showErrorMessage()
    }
}