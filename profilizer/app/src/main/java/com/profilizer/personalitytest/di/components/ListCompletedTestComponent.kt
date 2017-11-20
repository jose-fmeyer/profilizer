package com.profilizer.personalitytest.di.components

import com.profilizer.activities.ListCompletedTestsActivity
import com.profilizer.common.di.component.ActivityScope
import com.profilizer.personalitytest.di.modules.ListCompletedTestModule
import com.profilizer.personalitytest.di.modules.PersonalityTestModule
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(ListCompletedTestModule::class, PersonalityTestModule::class))
interface ListCompletedTestComponent {
    fun inject(listCompletedTestsActivity: ListCompletedTestsActivity)
}