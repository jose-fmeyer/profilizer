package com.profilizer.personalitytest.presenter

import android.util.Log
import com.profilizer.common.util.NetworkUtil
import com.profilizer.personalitytest.contracts.StartTestContract
import com.profilizer.personalitytest.model.PersonalityTest
import com.profilizer.personalitytest.services.PersonalityTestService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class StartTestPresenterImpl @Inject constructor(private val startTestView: StartTestContract.View,
                                                private val testService: PersonalityTestService) : StartTestContract.Presenter {
    private val tag = StartTestPresenterImpl::class.java.canonicalName
    private lateinit var disposable: CompositeDisposable

    override fun onAttach() {
        disposable = CompositeDisposable()
    }

    override fun onDetach() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
        disposable.clear()
    }

    override fun createPersonalityTest(userName: String) {
        startTestView.validateFields()
        if (NetworkUtil.isNotConnected(startTestView.getViewContext())) {
            startTestView.showNoNetworkMessage()
            return
        }
        startTestView.startCreating()
        val personalityTest = PersonalityTest(userName)
        disposable.add(
                testService.createTest(personalityTest)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ data ->
                        startTestView.onCreateFinish(data)
                    }, {
                        Log.e(tag, "Error on create test", it)
                        startTestView.showErrorMessage()
                    }))

    }

    override fun loadTestQuestions() {

    }
}