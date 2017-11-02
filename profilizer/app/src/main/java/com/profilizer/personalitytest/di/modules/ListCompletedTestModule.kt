package com.profilizer.personalitytest.di.modules

import com.profilizer.personalitytest.contracts.ListCompletedTestsContract
import com.profilizer.personalitytest.presenter.ListCompletedTestsPresenterImpl
import com.profilizer.personalitytest.services.AnswerService
import dagger.Module
import dagger.Provides

@Module
open class ListCompletedTestModule(var view: ListCompletedTestsContract.View) {

    @Provides
    fun provideListCompletedTestsView() = view

    @Provides
    open fun provideListCompletedTestsPresenter(service: AnswerService,
                                         view: ListCompletedTestsContract.View): ListCompletedTestsContract.Presenter {
        return ListCompletedTestsPresenterImpl(view, service)
    }
}