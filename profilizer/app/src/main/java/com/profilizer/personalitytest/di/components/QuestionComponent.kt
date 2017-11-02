package com.profilizer.personalitytest.di.components

import com.profilizer.common.component.ActivityScope
import com.profilizer.common.component.ApplicationComponent
import com.profilizer.fragments.QuestionFragment
import com.profilizer.personalitytest.di.modules.PersonalityTestModule
import com.profilizer.personalitytest.di.modules.QuestionModule
import dagger.Component

@ActivityScope
@Component(dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(QuestionModule::class, PersonalityTestModule::class))
interface QuestionComponent {
    fun inject(questionFragment: QuestionFragment)
}