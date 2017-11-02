package com.profilizer.personalitytest.contracts

import com.profilizer.common.BasePresenter
import com.profilizer.personalitytest.model.PersonalityTest
import com.profilizer.personalitytest.model.PersonalityTestQuestions

interface StartPersonalityTestTestContract {

    interface View {
        fun onFinishLoadingTestQuestions(personalityTestQuestions: PersonalityTestQuestions)
        fun showErrorMessage()
        fun startLoading()
    }

    interface Presenter: BasePresenter {
        fun loadTestQuestions()
    }
}