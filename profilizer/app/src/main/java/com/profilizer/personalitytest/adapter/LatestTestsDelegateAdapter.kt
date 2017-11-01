package com.profilizer.personalitytest.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.profilizer.R
import com.profilizer.common.ViewType
import com.profilizer.common.ViewTypeDelegateAdapter
import com.profilizer.common.inflate
import com.profilizer.personalitytest.model.PersonalityTest
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.android.synthetic.main.personalitytest_item.view.test_creation_date as creationDate
import kotlinx.android.synthetic.main.personalitytest_item.view.test_owner as owner
import kotlinx.android.synthetic.main.personalitytest_item.view.test_percentage_completion as percentageCompletion

class LatestTestsDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup) = LatestTestsViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType, position: Int) {
        holder as LatestTestsViewHolder
        holder.bind(item as PersonalityTest)
    }

    inner class LatestTestsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.personalitytest_item)) {

        fun bind(personalityTest: PersonalityTest) = with(itemView) {
            owner.text = personalityTest.owner
            val df = SimpleDateFormat("yyyy/MM/dd", Locale.US)
            creationDate.text = df.format(personalityTest.creationDate)
            percentageCompletion.text = String.format(
                    resources.getString(R.string.percentage_completion),
                    personalityTest.percentageCompletion)
        }
    }
}