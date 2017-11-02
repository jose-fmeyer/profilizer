package com.profilizer.common

import android.support.annotation.DrawableRes
import com.profilizer.R

enum class CategoryType(val category: String, @DrawableRes val imageResource : Int, val categoryName: String) {
    DEFAULT_CATEGORY("default", R.drawable.ic_hard_fact, "Default"),
    HARD_FACT("hard_fact", R.drawable.ic_hard_fact, "Hard Fact"),
    LIFESTYLE("lifestyle", R.drawable.ic_lifestyle, "Lifestyle"),
    INTROVERSION("introversion", R.drawable.ic_introversion, "Introversion"),
    PASSION("passion", R.drawable.ic_passion, "Passion");

    companion object {
        fun getCategoryTypeByCategory(category : String) : CategoryType {
            CategoryType.values().forEach {
                if (it.category.contentEquals(category)) {
                    return it
                }
            }
            return CategoryType.DEFAULT_CATEGORY
        }
    }
}