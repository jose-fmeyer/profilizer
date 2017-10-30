package com.profilizer.ui.base

import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.layout_app_bar.toolbar as toolbar

abstract class BaseActivity : AppCompatActivity() {

    override fun setTitle(@StringRes resId: Int) {
        toolbar?.let { toolbar ->
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(true)
            title = getString(resId)
        }
    }
}