package com.profilizer.personalitytest.presenter

import android.util.Log
import com.profilizer.common.util.NetworkUtil
import com.profilizer.personalitytest.contracts.LatestTestsContract
import com.profilizer.personalitytest.services.PersonalityTestService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LatestTestsPresenterImpl @Inject constructor(private val latestTestsView: LatestTestsContract.View,
                                                   private val testService: PersonalityTestService) : LatestTestsContract.Presenter {

    private val tag = LatestTestsPresenterImpl::class.java.canonicalName
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
        if (NetworkUtil.isNotConnected(latestTestsView.getViewContext())) {
            latestTestsView.showNoNetworkMessage()
            return
        }
        latestTestsView.onStartLoading()
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
                    Log.e(tag, "Error on loading tests", it)
                    latestTestsView.showErrorMessage()
                }))
    }
}