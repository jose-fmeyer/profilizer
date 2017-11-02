package com.profilizer.personalitytest.di.components

import com.profilizer.activities.LatestTestsActivity
import com.profilizer.activities.ListCompletedTestsActivity
import com.profilizer.common.component.ActivityScope
import com.profilizer.common.component.ApplicationComponent
import com.profilizer.personalitytest.di.modules.LatestTestsModule
import com.profilizer.personalitytest.di.modules.ListCompletedTestModule
import com.profilizer.personalitytest.di.modules.PersonalityTestModule
import dagger.Component

@ActivityScope
@Component(dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(ListCompletedTestModule::class, PersonalityTestModule::class))
interface ListCompletedTestComponent {
    fun inject(listCompletedTestsActivity: ListCompletedTestsActivity)
}