package com.profilizer.presenter

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.profilizer.common.ValidationException
import com.profilizer.personalitytest.contracts.StartTestContract
import com.profilizer.personalitytest.model.PersonalityTest
import com.profilizer.personalitytest.presenter.StartTestPresenterImpl
import com.profilizer.personalitytest.services.PersonalityTestService
import com.profilizer.common.util.TestUtils
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito
import org.mockito.Mock

class StartTestPresenterTest {

    @Mock lateinit var service: PersonalityTestService
    @Mock lateinit var view: StartTestContract.View
    private lateinit var presenter: StartTestContract.Presenter
    private lateinit var test : PersonalityTest
    private val testUtils = TestUtils()

    @Before
    fun setUp() {
        presenter = StartTestPresenterImpl(view, service)
        presenter.onAttach()
        test = testUtils.mockPersonalityTest()
    }

    @After
    fun onFinish() {
        presenter.onDetach()
    }

    @Test
    fun testCreatePersonalityTest() {
        BDDMockito.given(service.createTest(any())).willReturn(Observable.just(test))

        presenter.createPersonalityTest(TestUtils.OWNER)

        verify(view, times(1)).onCreateFinish(BDDMockito.any())
    }

    @Test
    fun testStartCreating() {
        BDDMockito.given(service.createTest(any())).willReturn(Observable.just(test))

        presenter.createPersonalityTest(TestUtils.OWNER)

        verify(view, times(1)).startCreating()
    }

    @Test
    fun testValidateFields() {
        BDDMockito.given(service.createTest(any())).willReturn(Observable.just(test))

        presenter.createPersonalityTest(TestUtils.OWNER)

        verify(view, times(1)).validateFields()
    }

    @Test(expected = ValidationException::class)
    fun testValidateFieldsMissingValue() {
        presenter.createPersonalityTest("")
    }

    @Test
    fun testShowErrorMessage() {
        BDDMockito.given(service.createTest(any())).willReturn(Observable.error(Throwable()))

        presenter.createPersonalityTest(TestUtils.OWNER)

        verify(view, times(1)).showErrorMessage()
    }
}