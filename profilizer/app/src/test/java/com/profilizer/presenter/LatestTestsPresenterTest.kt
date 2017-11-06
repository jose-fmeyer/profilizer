package com.profilizer.presenter

import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.profilizer.personalitytest.contracts.LatestTestsContract
import com.profilizer.personalitytest.model.PersonalityTest
import com.profilizer.personalitytest.presenter.LatestTestsPresenterImpl
import com.profilizer.personalitytest.services.PersonalityTestService
import com.profilizer.common.util.TestUtils
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.any
import org.mockito.BDDMockito.given
import org.mockito.Mock


class LatestTestsPresenterTest {

    @Mock lateinit var service: PersonalityTestService
    @Mock lateinit var view: LatestTestsContract.View
    private lateinit var presenter: LatestTestsContract.Presenter
    private lateinit var tests : List<PersonalityTest>
    private val testUtils = TestUtils()

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

        verify(view, times(1)).onStartLoading()
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