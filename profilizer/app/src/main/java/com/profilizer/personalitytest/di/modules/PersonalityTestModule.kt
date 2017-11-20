package com.profilizer.personalitytest.di.modules

import com.profilizer.personalitytest.services.AnswerService
import com.profilizer.personalitytest.services.PersonalityTestService

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
open class PersonalityTestModule {

    @Provides
    fun providesPersonalityTestService(retrofit : Retrofit) : PersonalityTestService =
            retrofit.create(PersonalityTestService::class.java)

    @Provides
    fun providesAnswerService(retrofit: Retrofit): AnswerService =
            retrofit.create(AnswerService::class.java)
}
