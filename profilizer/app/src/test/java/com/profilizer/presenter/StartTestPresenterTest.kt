package com.profilizer.presenter

import android.content.Context
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.profilizer.common.ValidationException
import com.profilizer.common.util.NetworkUtil
import com.profilizer.common.util.RxAndroidTestRule
import com.profilizer.common.util.TestUtils
import com.profilizer.personalitytest.contracts.StartTestContract
import com.profilizer.personalitytest.model.PersonalityTest
import com.profilizer.personalitytest.presenter.StartTestPresenterImpl
import com.profilizer.personalitytest.services.PersonalityTestService
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
class StartTestPresenterTest {

    @Rule val rxAndroidTestRule = RxAndroidTestRule()
    @Mock lateinit var service: PersonalityTestService
    @Mock lateinit var view: StartTestContract.View
    @Mock lateinit var context: Context
    private lateinit var presenter: StartTestContract.Presenter
    private lateinit var test : PersonalityTest
    private val testUtils = TestUtils()

    @Before
    fun setUp() {
        presenter = StartTestPresenterImpl(view, service)
        presenter.onAttach()
        test = testUtils.mockPersonalityTest()
        PowerMockito.mockStatic(NetworkUtil::class.java)
    }

    @After
    fun onFinish() {
        presenter.onDetach()
    }

    @Test
    fun testCreatePersonalityTest() {
        given(NetworkUtil.isNotConnected(any())).willReturn(false)
        given(service.createTest(any())).willReturn(Single.just(test))

        presenter.createPersonalityTest(TestUtils.OWNER)

        verify(view, times(1)).onCreateFinish(any())
    }

    @Test
    fun testStartCreating() {
        given(NetworkUtil.isNotConnected(any())).willReturn(false)
        given(service.createTest(any())).willReturn(Single.just(test))

        presenter.createPersonalityTest(TestUtils.OWNER)

        verify(view, times(1)).startCreating()
    }

    @Test
    fun testValidateFields() {
        given(NetworkUtil.isNotConnected(any())).willReturn(false)
        given(service.createTest(any())).willReturn(Single.just(test))

        presenter.createPersonalityTest(TestUtils.OWNER)

        verify(view, times(1)).validateFields()
    }

    @Test(expected = ValidationException::class)
    fun testValidateFieldsMissingValue() {
        given(view.validateFields()).willThrow(ValidationException("Validation Error"))
        presenter.createPersonalityTest("")
    }

    @Test
    fun testShowErrorMessage() {
        given(NetworkUtil.isNotConnected(any())).willReturn(false)
        given(service.createTest(any())).willReturn(Single.error(Throwable()))

        presenter.createPersonalityTest(TestUtils.OWNER)

        verify(view, times(1)).showErrorMessage()
    }

    @Test
    fun testShowNoNetworkConnectionMessage() {
        given(view.getViewContext()).willReturn(context)
        given(NetworkUtil.isNotConnected(any())).willReturn(true)

        presenter.createPersonalityTest(TestUtils.OWNER)

        verify(view, times(1)).showNoNetworkMessage()
    }
}