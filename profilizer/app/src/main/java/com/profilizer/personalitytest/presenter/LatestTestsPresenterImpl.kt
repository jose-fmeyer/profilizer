package com.profilizer.personalitytest.presenter

import com.profilizer.personalitytest.contracts.LatestTestsContract
import com.profilizer.personalitytest.services.PersonalityTestService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LatestTestsPresenterImpl @Inject constructor(val latestTestsView: LatestTestsContract.View,
                                                   val testService: PersonalityTestService) : LatestTestsContract.Presenter {

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

    override fun loadTests() {
        disposable.add(
                testService.loadTests()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ data ->
                    if (data.isEmpty()) {
                        latestTestsView.showEmptyResultMessage()
                    } else {
                        latestTestsView.showPersonalityTestData(data)
                    }
                }, {
                    latestTestsView.showErrorMessage()
                }))
    }
}