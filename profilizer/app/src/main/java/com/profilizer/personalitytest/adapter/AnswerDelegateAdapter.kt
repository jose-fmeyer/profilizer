package com.profilizer.personalitytest.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.profilizer.R
import com.profilizer.common.ViewType
import com.profilizer.common.ViewTypeDelegateAdapter
import com.profilizer.common.inflate
import com.profilizer.personalitytest.model.Answer
import kotlinx.android.synthetic.main.item_completed_test.view.text_answer as textAnswer
import kotlinx.android.synthetic.main.item_completed_test.view.text_question as textQuestion

class AnswerDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup) = AnswerViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType, position: Int) {
        holder as AnswerViewHolder
        holder.bind(item as Answer)
    }

    inner class AnswerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_completed_test)) {

        fun bind(answer: Answer) = with(itemView) {
            textQuestion.text = answer.question
            textAnswer.text = answer.answer
        }
    }
}