package com.profilizer.personalitytest.contracts

import com.profilizer.common.BasePresenter
import com.profilizer.common.BaseView
import com.profilizer.personalitytest.model.PersonalityTestQuestions

interface StartPersonalityTestTestContract {

    interface View: BaseView {
        fun onFinishLoadingTestQuestions(personalityTestQuestions: PersonalityTestQuestions)
        fun showErrorMessage()
        fun startLoading()
    }

    interface Presenter: BasePresenter {
        fun loadTestQuestions()
    }
}