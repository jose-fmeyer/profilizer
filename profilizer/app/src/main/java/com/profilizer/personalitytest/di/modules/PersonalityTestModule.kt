package com.profilizer.personalitytest.di.modules

import com.profilizer.personalitytest.services.PersonalityTestService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class PersonalityTestModule {

    @Provides
    fun providesPersonalityTestService(retrofit: Retrofit): PersonalityTestService {
        return retrofit.create(PersonalityTestService::class.java)
    }
}