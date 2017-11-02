package com.profilizer.personalitytest.di.modules

import com.profilizer.personalitytest.contracts.QuestionContract
import com.profilizer.personalitytest.contracts.StartTestContract
import com.profilizer.personalitytest.presenter.QuestionPresenterImpl
import com.profilizer.personalitytest.presenter.StartTestPresenterImpl
import com.profilizer.personalitytest.services.AnswerService
import com.profilizer.personalitytest.services.PersonalityTestService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
open class QuestionModule(var view: QuestionContract.View) {

    @Provides
    fun provideQuestionView() = view

    @Provides
    open fun provideQuestionPresenter(service: AnswerService,
                                       view: QuestionContract.View): QuestionContract.Presenter {
        return QuestionPresenterImpl(view, service)
    }
}