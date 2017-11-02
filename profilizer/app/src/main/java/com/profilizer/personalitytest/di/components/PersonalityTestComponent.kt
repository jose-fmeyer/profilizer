package com.profilizer.personalitytest.di.components

import com.profilizer.activities.LatestTestsActivity
import com.profilizer.activities.StartPersonalityTestActivity
import com.profilizer.common.component.ActivityScope
import com.profilizer.common.component.ApplicationComponent
import com.profilizer.fragments.QuestionFragment
import com.profilizer.fragments.StartTestFragment
import com.profilizer.personalitytest.di.modules.PersonalityTestModule
import com.profilizer.personalitytest.di.modules.StartPersonalityTestModule
import com.profilizer.personalitytest.di.modules.QuestionModule
import dagger.Component

@ActivityScope
@Component(dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(PersonalityTestModule::class))
interface PersonalityTestComponent {

}