package com.profilizer.ui

import android.content.Intent
import android.os.Bundle
import com.profilizer.R
import com.profilizer.ui.base.BaseActivity
import com.profilizer.ui.test.PersonalityTestActivity
import kotlinx.android.synthetic.main.activity_latest_tests.btn_start as btnStart

class LatestTestsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_tests)
        setTitle(R.string.latest_tests)
        btnStart.setOnClickListener {
            startActivity(Intent(this, PersonalityTestActivity::class.java))
        }
    }
}
