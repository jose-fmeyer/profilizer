package com.profilizer.personalitytest.services

import com.profilizer.personalitytest.model.PersonalityTest
import io.reactivex.Observable
import retrofit2.http.GET

interface PersonalityTestService {

    @GET("/tests")
    fun loadTests(): Observable<List<PersonalityTest>>
}