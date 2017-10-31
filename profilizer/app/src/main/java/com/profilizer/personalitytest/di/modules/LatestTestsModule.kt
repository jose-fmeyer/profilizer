package com.profilizer.personalitytest.di.modules

import com.profilizer.personalitytest.contracts.LatestTestsContract
import com.profilizer.personalitytest.presenter.LatestTestsPresenterImpl
import com.profilizer.personalitytest.services.PersonalityTestService
import dagger.Module
import dagger.Provides

@Module
open class LatestTestsModule(var view: LatestTestsContract.View) {

    @Provides
    fun provideLatestTestsView() = view

    @Provides
    open fun provideLatestTestsPresenter(service: PersonalityTestService,
                                         view: LatestTestsContract.View): LatestTestsContract.Presenter {
        return LatestTestsPresenterImpl(view, service)
    }
}