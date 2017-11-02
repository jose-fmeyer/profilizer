package com.profilizer.personalitytest.services

import com.profilizer.personalitytest.model.PersonalityTest
import com.profilizer.personalitytest.model.PersonalityTestQuestions
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PersonalityTestService {

    @GET("tests")
    fun loadTests(): Observable<List<PersonalityTest>>

    @GET("tests/questions")
    fun loadTestsQuestions(): Observable<PersonalityTestQuestions>

    @POST("tests")
    fun createTest(@Body personalityTest: PersonalityTest): Observable<PersonalityTest>
}