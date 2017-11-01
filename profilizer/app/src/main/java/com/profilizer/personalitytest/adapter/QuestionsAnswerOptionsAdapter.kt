package com.profilizer.personalitytest.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.profilizer.R
import com.profilizer.common.inflate
import com.profilizer.personalitytest.adapter.QuestionsAnswerOptionsAdapter.QuestionTypeTestsViewHolder
import kotlinx.android.synthetic.main.list_item_question.view.question
import kotlinx.android.synthetic.main.list_item_question.view.radio_group as questionsRadioGroup


class QuestionsAnswerOptionsAdapter(private var options: Set<String>) : RecyclerView.Adapter<QuestionTypeTestsViewHolder>() {

    private var lastCheckedRadioGroup: RadioGroup? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionTypeTestsViewHolder =
            QuestionTypeTestsViewHolder(parent)

    override fun onBindViewHolder(holder: QuestionTypeTestsViewHolder, position: Int) {
        holder.bind(options.elementAt(position), position)
    }

    override fun getItemCount(): Int = options.size

    fun getUserAnswer(): String? {
        lastCheckedRadioGroup?.let {
            return options.elementAt(it.checkedRadioButtonId)
        }
        return null
    }

    inner class QuestionTypeTestsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.list_item_question)) {

        fun bind(option: String, position: Int) = with(itemView) {
            question.text = option
            val radioQuestion = RadioButton(context)
            radioQuestion.id = position
            radioQuestion.buttonTintList = ContextCompat.getColorStateList(context, R.color.single_choice_state)
            questionsRadioGroup.addView(radioQuestion)
            questionsRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
                lastCheckedRadioGroup?.apply {
                    if (checkedRadioButtonId != radioGroup.checkedRadioButtonId
                            && checkedRadioButtonId != RadioGroup.NO_ID) {
                        clearCheck()
                    }
                }
                lastCheckedRadioGroup = radioGroup
            }
        }
    }
}