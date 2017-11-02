package com.profilizer.personalitytest.contracts

import com.profilizer.common.BasePresenter
import com.profilizer.personalitytest.model.Answer

interface QuestionContract {

    interface View {
        fun showErrorMessage()
        fun onStartLoading()
        fun onFinishSaveAnswer(answer: Answer)
    }

    interface Presenter: BasePresenter {
        fun saveAnswer(answer: String, question: String, category: String, personalityTestId: String)
    }
}