package com.profilizer.personalitytest.di.modules

import com.profilizer.personalitytest.contracts.StartTestContract
import com.profilizer.personalitytest.presenter.StartTestPresenterImpl
import com.profilizer.personalitytest.services.PersonalityTestService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
open class StartTestModule(var view: StartTestContract.View) {

    @Provides
    fun provideStartTestView() = view

    @Provides
    open fun provideStartTestPresenter(service: PersonalityTestService,
                                         view: StartTestContract.View): StartTestContract.Presenter {
        return StartTestPresenterImpl(view, service)
    }
}