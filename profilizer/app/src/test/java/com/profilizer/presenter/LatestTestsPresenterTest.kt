package com.profilizer.presenter

import android.content.Context
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.profilizer.common.util.NetworkUtil
import com.profilizer.common.util.RxAndroidTestRule
import com.profilizer.common.util.TestUtils
import com.profilizer.personalitytest.contracts.LatestTestsContract
import com.profilizer.personalitytest.model.PersonalityTest
import com.profilizer.personalitytest.presenter.LatestTestsPresenterImpl
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
class LatestTestsPresenterTest {

    @Rule val rxAndroidTestRule = RxAndroidTestRule()
    @Mock lateinit var service: PersonalityTestService
    @Mock lateinit var view: LatestTestsContract.View
    @Mock lateinit var context: Context
    private lateinit var presenter: LatestTestsContract.Presenter
    private lateinit var tests : List<PersonalityTest>
    private val testUtils = TestUtils()

    @Before
    fun setUp() {
        presenter = LatestTestsPresenterImpl(view, service)
        presenter.onAttach()
        tests = listOf(testUtils.mockPersonalityTest())
        PowerMockito.mockStatic(NetworkUtil::class.java)
    }

    @After
    fun onFinish() {
        presenter.onDetach()
    }

    @Test
    fun testLoadTests() {
        given(NetworkUtil.isNotConnected(any())).willReturn(false)
        given(service.loadTests()).willReturn(Single.just(tests))

        presenter.loadTests()

        verify(view, times(1)).showPersonalityTestData(any())
    }

    @Test
    fun testStartLoading() {
        given(NetworkUtil.isNotConnected(any())).willReturn(false)
        given(service.loadTests()).willReturn(Single.just(tests))

        presenter.loadTests()

        verify(view, times(1)).onStartLoading()
    }

    @Test
    fun testShowEmptyResultMessage() {
        given(NetworkUtil.isNotConnected(any())).willReturn(false)
        given(service.loadTests()).willReturn(Single.just(emptyList()))

        presenter.loadTests()

        verify(view, times(1)).showEmptyResultMessage()
    }

    @Test
    fun testShowErrorMessage() {
        given(NetworkUtil.isNotConnected(any())).willReturn(false)
        given(service.loadTests()).willReturn(Single.error(Throwable()))

        presenter.loadTests()

        verify(view, times(1)).showErrorMessage()
    }

    @Test
    fun testShowNoNetworkConnectionMessage() {
        given(view.getViewContext()).willReturn(context)
        given(NetworkUtil.isNotConnected(any())).willReturn(true)

        presenter.loadTests()

        verify(view, times(1)).showNoNetworkMessage()
    }
}