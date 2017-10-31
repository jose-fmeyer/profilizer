package com.profilizer.presenter

import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.profilizer.personalitytest.presenter.LatestTestsPresenterImpl
import com.profilizer.personalitytest.contracts.LatestTestsContract
import com.profilizer.personalitytest.services.PersonalityTestService
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import com.profilizer.personalitytest.model.PersonalityTest
import com.profilizer.presenter.util.TestUtils
import io.reactivex.Observable
import org.mockito.BDDMockito.any
import org.mockito.BDDMockito.given



class LatestTestsPresenterTest {

    @Mock lateinit var service: PersonalityTestService
    @Mock lateinit var view: LatestTestsContract.View
    lateinit var presenter: LatestTestsContract.Presenter
    lateinit var tests : List<PersonalityTest>
    val testUtils = TestUtils()

    @Before
    fun setUp() {
        presenter = LatestTestsPresenterImpl(view, service)
        presenter.onAttach()
        tests = listOf(testUtils.mockPersonalityTest())
    }

    @After
    fun onFinish() {
        presenter.onDetach()
    }

    @Test
    fun testLoadTests() {
        given(service.loadTests()).willReturn(Observable.just(tests))

        presenter.loadTests()

        verify(view, times(1)).showPersonalityTestData(any())
    }

    @Test
    fun testStartLoading() {
        given(service.loadTests()).willReturn(Observable.just(tests))

        presenter.loadTests()

        verify(view, times(1)).startLoading()
    }

    @Test
    fun testShowEmptyResultMessage() {
        given(service.loadTests()).willReturn(Observable.just(emptyList()))

        presenter.loadTests()

        verify(view, times(1)).showEmptyResultMessage()
    }

    @Test
    fun testShowErrorMessage() {
        given(service.loadTests()).willReturn(Observable.error(Throwable()))

        presenter.loadTests()

        verify(view, times(1)).showErrorMessage()
    }
}