package com.profilizer.presenter

import android.content.Context
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.profilizer.common.util.NetworkUtil
import com.profilizer.common.util.RxAndroidTestRule
import com.profilizer.common.util.TestUtils
import com.profilizer.personalitytest.contracts.StartPersonalityTestTestContract
import com.profilizer.personalitytest.model.PersonalityTestQuestions
import com.profilizer.personalitytest.presenter.StartPersonalityTestPresenterImpl
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
class StartPersonalityPresenterTest {

    @Rule val rxAndroidTestRule = RxAndroidTestRule()
    @Mock lateinit var service: PersonalityTestService
    @Mock lateinit var view: StartPersonalityTestTestContract.View
    @Mock lateinit var context: Context
    private lateinit var presenter: StartPersonalityTestTestContract.Presenter
    private lateinit var testQuestions : PersonalityTestQuestions
    private val testUtils = TestUtils()

    @Before
    fun setUp() {
        presenter = StartPersonalityTestPresenterImpl(view, service)
        presenter.onAttach()
        testQuestions = testUtils.mockQuestionTestQuestions()
        PowerMockito.mockStatic(NetworkUtil::class.java)
    }

    @After
    fun onFinish() {
        presenter.onDetach()
    }

    @Test
    fun testLoadTestQuestions() {
        given(NetworkUtil.isNotConnected(any())).willReturn(false)
        given(service.loadTestsQuestions()).willReturn(Single.just(testQuestions))

        presenter.loadTestQuestions()

        verify(view, times(1)).onFinishLoadingTestQuestions(any())
    }

    @Test
    fun testStartLoading() {
        given(NetworkUtil.isNotConnected(any())).willReturn(false)
        given(service.loadTestsQuestions()).willReturn(Single.just(testQuestions))

        presenter.loadTestQuestions()

        verify(view, times(1)).startLoading()
    }

    @Test
    fun testShowErrorMessage() {
        given(NetworkUtil.isNotConnected(any())).willReturn(false)
        given(service.loadTestsQuestions()).willReturn(Single.error(Throwable()))

        presenter.loadTestQuestions()

        verify(view, times(1)).showErrorMessage()
    }

    @Test
    fun testShowNoNetworkConnectionMessage() {
        given(view.getViewContext()).willReturn(context)
        given(NetworkUtil.isNotConnected(any())).willReturn(true)

        presenter.loadTestQuestions()

        verify(view, times(1)).showNoNetworkMessage()
    }
}