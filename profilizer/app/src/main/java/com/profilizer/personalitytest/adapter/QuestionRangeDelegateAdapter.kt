package com.profilizer.personalitytest.adapter

import android.support.v7.widget.AppCompatSeekBar
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.SeekBar
import com.profilizer.R
import com.profilizer.common.ViewType
import com.profilizer.common.ViewTypeDelegateAdapter
import com.profilizer.common.inflate
import com.profilizer.personalitytest.model.QuestionRange
import com.profilizer.ui.OnSeekBarChangeListenerAdapter
import kotlinx.android.synthetic.main.list_item_question_range.view.progress_label as progressLabel
import kotlinx.android.synthetic.main.list_item_question_range.view.range_bar as rangeBar

class QuestionRangeDelegateAdapter(private var questionRangeViewType : ViewType) : ViewTypeDelegateAdapter {

    private var rangeValueSelected: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup) = QuestionRangeViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType, position: Int) {
        holder as QuestionRangeViewHolder
        val questionRange = questionRangeViewType as QuestionRange
        rangeValueSelected = questionRange.from
        holder.bind(questionRange.from, questionRange.to)
    }

    override fun getViewHolderFieldValue(): String? {
        return rangeValueSelected.toString()
    }

    inner class QuestionRangeViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.list_item_question_range)) {

        fun bind(from: Int, to: Int) = with(itemView) {
            val supportRangeBar = rangeBar as AppCompatSeekBar
            progressLabel.text = from.toString()
            supportRangeBar.progress = from
            supportRangeBar.max = to

            supportRangeBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListenerAdapter() {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    rangeValueSelected = progress
                    progressLabel.text = progress.toString()
                }
            })
        }
    }
}