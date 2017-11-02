package com.profilizer.common

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

interface ViewTypeDelegateAdapter {

    companion object {
        val LATEST_TESTS = 1
        val QUESTION_TYPE_SINGLE_CHOICE = 2
        val QUESTION_TYPE_RANGE = 3
        val HEADER_TYPE = 4
        val ANSWER_TYPE = 5
    }

    fun onCreateViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType, position: Int = 0)

    fun getViewHolderFieldValue() : String? = ""
}
