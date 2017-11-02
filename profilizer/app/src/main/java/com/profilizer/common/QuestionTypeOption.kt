package com.profilizer.common

enum class QuestionTypeOption(val questionType: String) {
    NUMBER_RANGE("number_range"),
    SINGLE_CHOICE("single_choice");

    companion object {
        fun getQuestionTypeByType(questionType : String) : QuestionTypeOption {
            QuestionTypeOption.values().forEach {
                if (it.questionType.contentEquals(questionType)) {
                    return it
                }
            }
            return SINGLE_CHOICE
        }
    }
}