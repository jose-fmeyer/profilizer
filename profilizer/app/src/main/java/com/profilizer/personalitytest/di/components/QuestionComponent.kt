package com.profilizer.personalitytest.di.components

import com.profilizer.common.di.component.ActivityScope
import com.profilizer.fragments.QuestionFragment
import com.profilizer.personalitytest.di.modules.PersonalityTestModule
import com.profilizer.personalitytest.di.modules.QuestionModule
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(QuestionModule::class, PersonalityTestModule::class))
interface QuestionComponent {
    fun inject(questionFragment: QuestionFragment)
}