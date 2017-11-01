package com.profilizer.personalitytest.model

import com.profilizer.common.ViewType
import com.profilizer.common.ViewTypeDelegateAdapter
import java.io.Serializable
import java.util.*

data class Category(var name: String?)

data class ConditionPredicate(var exactEquals: List<String>?)

data class PersonalityTest(var owner: String,
                           val id: String = "",
                           var percentageCompletion: Int = 0,
                           var creationDate: Date = Date()): Serializable, ViewType {

    override fun getViewType() = ViewTypeDelegateAdapter.LATEST_TESTS
}

data class PersonalityTestQuestions(var id: String = "",
                                    var questions: List<Question>,
                                    var categories: List<Category>?)

data class Question(var question: String, var category: String, var questionType: QuestionType)

data class QuestionCondition(var predicate: ConditionPredicate?, var conditionalQuestion: Question?)

data class QuestionRange(var from: Int = 0, var to: Int = 0)

data class QuestionType(var type: String,
                        var options: Set<String>,
                        var condition: QuestionCondition?,
                        var range: QuestionRange?)