package com.profilizer.personalitytest.adapter

import com.profilizer.common.ViewTypeDelegateAdapter.Companion.QUESTION_TYPE_SINGLE_CHOICE
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.RadioGroup
import com.profilizer.common.ViewType
import com.profilizer.common.ViewTypeDelegateAdapter
import kotlinx.android.synthetic.main.list_item_question_single_choice.view.radio_group as questionsRadioGroup
import kotlinx.android.synthetic.main.list_item_question_single_choice.view.tv_question as tvQuestion


class QuestionsAnswerOptionsAdapter(private var questionType: ViewType) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var delegateAdapter : ViewTypeDelegateAdapter =
            if (questionType.getViewType() == QUESTION_TYPE_SINGLE_CHOICE) {
                QuestionSingleChoiceDelegateAdapter(questionType)
            } else {
                QuestionRangeDelegateAdapter(questionType)
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            delegateAdapter.onCreateViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
            delegateAdapter.onBindViewHolder(holder, questionType, position)

    override fun getItemCount(): Int = questionType.getTotalItems()

    fun getUserAnswer(): String? {
        return delegateAdapter.getViewHolderFieldValue()
    }

    fun getSelectedPosition() : Int {
        if (questionType.getViewType() == QUESTION_TYPE_SINGLE_CHOICE) {
            val delegate = delegateAdapter as QuestionSingleChoiceDelegateAdapter
            return delegate.getSelectedPosition()
        }
        return RadioGroup.NO_ID
    }
}