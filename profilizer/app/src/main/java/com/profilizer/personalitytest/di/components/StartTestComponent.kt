package com.profilizer.personalitytest.di.components

import com.profilizer.common.di.component.ActivityScope
import com.profilizer.fragments.StartTestFragment
import com.profilizer.personalitytest.di.modules.PersonalityTestModule
import com.profilizer.personalitytest.di.modules.StartTestModule
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(StartTestModule::class, PersonalityTestModule::class))
interface StartTestComponent {
    fun inject(startTestFragment: StartTestFragment)
}