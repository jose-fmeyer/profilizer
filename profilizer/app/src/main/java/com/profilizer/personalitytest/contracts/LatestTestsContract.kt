package com.profilizer.personalitytest.contracts

import com.profilizer.common.BasePresenter
import com.profilizer.personalitytest.model.PersonalityTest

interface LatestTestsContract {

    interface View {
        fun showPersonalityTestData(tests: List<PersonalityTest>)
        fun showErrorMessage()
        fun showEmptyResultMessage()
        fun onStartLoading()
    }

    interface Presenter: BasePresenter {
        fun loadTests()
    }
}