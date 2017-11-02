package com.profilizer.personalitytest.presenter

import android.util.Log
import com.profilizer.personalitytest.contracts.StartPersonalityTestTestContract
import com.profilizer.personalitytest.model.Question
import com.profilizer.personalitytest.services.PersonalityTestService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class StartPersonalityTestPresenterImpl @Inject constructor(private val testTestViewStart: StartPersonalityTestTestContract.View,
                                                            private val testService: PersonalityTestService) : StartPersonalityTestTestContract.Presenter {

    private val tag = QuestionPresenterImpl::class.java.canonicalName
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

    override fun loadTestQuestions() {
        testTestViewStart.startLoading()
        disposable.add(
                testService.loadTestsQuestions()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ data ->
                            if (data != null) {
                                testTestViewStart.onFinishLoadingTestQuestions(data)
                            } else {
                                testTestViewStart.showErrorMessage()
                            }
                        }, {
                            Log.e(tag, "Error on loading tests", it)
                            testTestViewStart.showErrorMessage()
                        }))
    }
}