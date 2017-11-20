package com.profilizer.common.di.component

import android.app.Application
import com.profilizer.ProfilizerApplication
import com.profilizer.common.di.module.AppModule
import com.profilizer.common.di.module.NetworkModule
import com.profilizer.personalitytest.di.components.*
import com.profilizer.personalitytest.di.modules.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, NetworkModule::class))
interface ApplicationComponent {
    fun inject(application: ProfilizerApplication)

    fun provideStartTestComponent(startTestModule: StartTestModule,
                                  personalityTestModule: PersonalityTestModule) : StartTestComponent

    fun provideStartPersonalityTestComponent(startPersonalityTestModule: StartPersonalityTestModule,
                                             personalityTestModule: PersonalityTestModule) : StartPersonalityTestComponent

    fun provideQuestionComponent(questionModule: QuestionModule,
                                 personalityTestModule: PersonalityTestModule) : QuestionComponent

    fun provideListCompletedTestComponent(listCompletedTestModule: ListCompletedTestModule,
                                          personalityTestModule: PersonalityTestModule) : ListCompletedTestComponent

    fun provideLatestTestsComponent(latestTestsModule: LatestTestsModule,
                                    personalityTestModule: PersonalityTestModule) : LatestTestsComponent

    companion object {

        fun init(): ApplicationComponent {
            return DaggerApplicationComponent.builder()
                    .networkModule(NetworkModule())
                    .build()
        }
    }
}