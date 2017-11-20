package com.profilizer.personalitytest.services

import com.profilizer.personalitytest.model.Answer
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AnswerService {

    @GET("answers")
    fun loadAnswers(): Single<List<Answer>>

    @GET("answers/{testId}")
    fun loadCompletedTestAnswers(@Path("testId") personalityTestId: String): Single<List<Answer>>

    @POST("answers")
    fun createAnswer(@Body answer: Answer): Single<Answer>
}