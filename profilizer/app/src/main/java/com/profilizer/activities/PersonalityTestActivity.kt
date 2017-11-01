package com.profilizer.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.profilizer.R
import com.profilizer.common.visible
import com.profilizer.fragments.QuestionFragment
import com.profilizer.fragments.StartTestFragment
import kotlinx.android.synthetic.main.activity_personality_test.container_test as testContainer
import kotlinx.android.synthetic.main.activity_personality_test.progress_layout as progressLayout

class PersonalityTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personality_test)
        loadStartFragment()
    }

    private fun loadStartFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container_test, StartTestFragment())
                .commit()
    }

    fun loadQuestionFragment() {
        progressLayout.visible()
        supportFragmentManager.beginTransaction()
                .replace(R.id.container_test, QuestionFragment())
                .commit()
    }
}
