package com.profilizer.personalitytest.di.components

import com.profilizer.activities.LatestTestsActivity
import com.profilizer.common.di.component.ActivityScope
import com.profilizer.personalitytest.di.modules.LatestTestsModule
import com.profilizer.personalitytest.di.modules.PersonalityTestModule
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(LatestTestsModule::class, PersonalityTestModule::class))
interface LatestTestsComponent {
    fun inject(latestTestsActivity: LatestTestsActivity)
}