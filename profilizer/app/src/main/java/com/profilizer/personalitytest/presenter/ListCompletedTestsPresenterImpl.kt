package com.profilizer.personalitytest.presenter

import android.util.Log
import com.profilizer.personalitytest.contracts.ListCompletedTestsContract
import com.profilizer.personalitytest.services.AnswerService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListCompletedTestsPresenterImpl @Inject constructor(private val completedTestView: ListCompletedTestsContract.View,
                                                          private val answerService: AnswerService) : ListCompletedTestsContract.Presenter {

    private val tag = ListCompletedTestsPresenterImpl::class.java.canonicalName
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

    override fun loadCompletedTestData(personalityTestId: String) {
        completedTestView.onStartLoading()
        disposable.add(
                answerService.loadCompletedTestAnswers(personalityTestId)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ data ->
                            completedTestView.showCompletedTestData(data)
                        }, {
                            Log.e(tag, "Error on loading test answers", it)
                            completedTestView.showErrorMessage()
                        }))
    }
}