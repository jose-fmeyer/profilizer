package com.profilizer.presenter.util

import com.profilizer.common.CategoryType
import com.profilizer.personalitytest.model.*
import java.util.*

class TestUtils {

    companion object {
        val TEST_ID = "1234abc"
        val OWNER = "User 1"
        val ANSWER = "male"
        val QUESTION = "Question option"
    }

    fun mockPersonalityTest() = PersonalityTest(id = TEST_ID,
            owner = OWNER, creationDate = Date(), percentageCompletion = 10)

    fun mockAnswer() = Answer(id = TEST_ID, answer = ANSWER,
            question = QUESTION, category = CategoryType.INTROVERSION.category,
            personalityTestId = TEST_ID, creationDate = Date())

    fun mockQuestionTestQuestions() = PersonalityTestQuestions(id = TEST_ID, questions = listOf(mockQuestion()),
            categories = null)

    fun mockQuestion() = Question(QUESTION, category = CategoryType.INTROVERSION.category,
            questionType = QuestionType(type = "single_choice", options = listOf("male"),
                    condition = null, range = QuestionRange(from = 10, to = 100)))


}