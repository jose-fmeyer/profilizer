package com.profilizer.personalitytest.di.modules

import com.profilizer.personalitytest.contracts.StartPersonalityTestTestContract
import com.profilizer.personalitytest.presenter.StartPersonalityTestPresenterImpl
import com.profilizer.personalitytest.services.PersonalityTestService
import dagger.Module
import dagger.Provides

@Module
open class StartPersonalityTestModule(var view: StartPersonalityTestTestContract.View) {

    @Provides
    fun providePersonalityTestView() = view

    @Provides
    fun providePersonalityTestPresenter(testService: PersonalityTestService,
                                      view: StartPersonalityTestTestContract.View): StartPersonalityTestTestContract.Presenter {
        return StartPersonalityTestPresenterImpl(view, testService)
    }
}