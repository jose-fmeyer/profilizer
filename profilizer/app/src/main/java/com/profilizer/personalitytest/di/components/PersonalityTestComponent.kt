package com.profilizer.personalitytest.di.components

import com.profilizer.common.component.ActivityScope
import com.profilizer.common.component.ApplicationComponent
import com.profilizer.personalitytest.di.modules.PersonalityTestModule
import dagger.Component

@ActivityScope
@Component(dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(PersonalityTestModule::class))
interface PersonalityTestComponent {

}