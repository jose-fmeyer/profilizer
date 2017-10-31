package com.profilizer.presenter.util

import com.profilizer.personalitytest.model.PersonalityTest
import java.util.*

class TestUtils {

    companion object {
        val TEST_ID = "1234abc"
        val OWNER = "User 1"

    }

    fun mockPersonalityTest() = PersonalityTest(id = TEST_ID,
            owner = OWNER, creationDate = Date(), percentageCompletion = 10)
}