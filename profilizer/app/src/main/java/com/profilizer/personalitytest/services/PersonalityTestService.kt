package com.profilizer.personalitytest.services

import com.profilizer.personalitytest.model.PersonalityTest
import com.profilizer.personalitytest.model.PersonalityTestQuestions
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PersonalityTestService {

    @GET("tests")
    fun loadTests(): Single<List<PersonalityTest>>

    @GET("tests/questions")
    fun loadTestsQuestions(): Single<PersonalityTestQuestions>

    @POST("tests")
    fun createTest(@Body personalityTest: PersonalityTest): Single<PersonalityTest>
}