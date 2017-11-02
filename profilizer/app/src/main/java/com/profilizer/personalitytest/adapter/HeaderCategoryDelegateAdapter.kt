package com.profilizer.personalitytest.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.profilizer.R
import com.profilizer.common.ViewType
import com.profilizer.common.ViewTypeDelegateAdapter
import com.profilizer.common.inflate
import com.profilizer.personalitytest.model.Category
import kotlinx.android.synthetic.main.item_header_category.view.text_category as textCategory

class HeaderCategoryDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup) = HeaderCategoryViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType, position: Int) {
        holder as HeaderCategoryViewHolder
        holder.bind(item as Category)
    }

    inner class HeaderCategoryViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_header_category)) {

        fun bind(category: Category) = with(itemView) {
            textCategory.text = category.name
        }
    }
}