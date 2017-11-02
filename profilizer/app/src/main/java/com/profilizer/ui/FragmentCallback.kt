package com.profilizer.ui

import com.profilizer.personalitytest.model.Answer

interface FragmentCallback {
    fun onQuestionFinish(answer: Answer)
    fun onStartFinish(personalityTestId : String)
    fun onStartLoading()
    fun onFinishLoading()
}