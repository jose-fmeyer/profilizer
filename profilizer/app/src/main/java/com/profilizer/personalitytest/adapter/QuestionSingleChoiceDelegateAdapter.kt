package com.profilizer.personalitytest.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.profilizer.R
import com.profilizer.common.ViewType
import com.profilizer.common.ViewTypeDelegateAdapter
import com.profilizer.common.inflate
import com.profilizer.personalitytest.model.QuestionSingleChoice
import kotlinx.android.synthetic.main.list_item_question_single_choice.view.tv_question as tvQuestion
import kotlinx.android.synthetic.main.list_item_question_single_choice.view.radio_group as questionsRadioGroup

class QuestionSingleChoiceDelegateAdapter(private var questionSingleChoiceViewType : ViewType) :
        ViewTypeDelegateAdapter {

    private var lastCheckedRadioGroup: RadioGroup? = null

    override fun onCreateViewHolder(parent: ViewGroup) = QuestionSingleChoiceViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType, position: Int) {
        holder as QuestionSingleChoiceViewHolder
        val questionSingleChoice = questionSingleChoiceViewType as QuestionSingleChoice
        holder.bind(questionSingleChoice.options.elementAt(position), position)
    }

    fun getSelectedPosition() : Int {
        lastCheckedRadioGroup?.let {
            return it.checkedRadioButtonId
        }
        return RadioGroup.NO_ID
    }

    override fun getViewHolderFieldValue(): String? {
        lastCheckedRadioGroup?.let {
            val questionSingleChoice = questionSingleChoiceViewType as QuestionSingleChoice
            return questionSingleChoice.options.elementAt(it.checkedRadioButtonId)
        }
        return null
    }

    inner class QuestionSingleChoiceViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.list_item_question_single_choice)) {

        fun bind(option: String, position: Int) = with(itemView) {
            tvQuestion.text = option
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