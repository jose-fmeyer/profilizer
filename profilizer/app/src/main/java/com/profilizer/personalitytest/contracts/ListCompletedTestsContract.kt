package com.profilizer.personalitytest.contracts

import com.profilizer.common.BasePresenter
import com.profilizer.personalitytest.model.Answer

interface ListCompletedTestsContract {

    interface View {
        fun showCompletedTestData(answers: List<Answer>)
        fun showErrorMessage()
        fun onStartLoading()
    }

    interface Presenter: BasePresenter {
        fun loadCompletedTestData(personalityTestId: String)
    }
}