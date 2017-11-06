package com.profilizer.personalitytest.contracts

import com.profilizer.common.BasePresenter
import com.profilizer.common.BaseView
import com.profilizer.personalitytest.model.PersonalityTest

interface LatestTestsContract {

    interface View: BaseView {
        fun showPersonalityTestData(tests: List<PersonalityTest>)
        fun showErrorMessage()
        fun showEmptyResultMessage()
        fun onStartLoading()
    }

    interface Presenter: BasePresenter {
        fun loadTests()
    }
}