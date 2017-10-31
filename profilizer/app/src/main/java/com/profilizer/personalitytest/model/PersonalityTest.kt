package com.profilizer.personalitytest.model

import com.profilizer.common.ViewType
import com.profilizer.common.ViewTypeDelegateAdapter
import java.io.Serializable
import java.util.*

data class PersonalityTest(val id: String,
                           var owner: String,
                           var percentageCompletion: Int = 0,
                           var creationDate: Date): Serializable, ViewType {

    override fun getViewType() = ViewTypeDelegateAdapter.LATEST_TESTS
}
