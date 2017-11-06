package com.profilizer.common

import android.content.Context

interface BaseView {
    fun getViewContext(): Context
    fun showNoNetworkMessage()
}