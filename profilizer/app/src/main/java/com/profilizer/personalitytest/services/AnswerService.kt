package com.profilizer.personalitytest.services

import com.profilizer.personalitytest.model.Answer
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AnswerService {

    @GET("answers")
    fun loadAnswers(): Observable<List<Answer>>

    @GET("answers/{testId}")
    fun loadCompletedTestAnswers(@Path("testId") personalityTestId: String): Observable<List<Answer>>

    @POST("answers")
    fun createAnswer(@Body answer: Answer): Observable<Answer>
}