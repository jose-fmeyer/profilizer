package com.profilizer.personalitytest.di.components

import com.profilizer.activities.StartPersonalityTestActivity
import com.profilizer.common.di.component.ActivityScope
import com.profilizer.personalitytest.di.modules.PersonalityTestModule
import com.profilizer.personalitytest.di.modules.StartPersonalityTestModule
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(StartPersonalityTestModule::class, PersonalityTestModule::class))
interface StartPersonalityTestComponent {
    fun inject(startPersonalityTestActivity: StartPersonalityTestActivity)
}