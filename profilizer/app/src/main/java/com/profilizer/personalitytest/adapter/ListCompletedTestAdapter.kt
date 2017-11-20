package com.profilizer.personalitytest.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.profilizer.common.CategoryType
import com.profilizer.common.ViewType
import com.profilizer.common.ViewTypeDelegateAdapter
import com.profilizer.personalitytest.model.Answer
import com.profilizer.personalitytest.model.Category
import java.util.*
import kotlin.collections.HashMap

class ListCompletedTestAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    private var items: MutableList<ViewType>
    private var itemsSearchingData: MutableList<ViewType>
    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()
    private val queryFilter = ItemFilter()

    init {
        delegateAdapters.append(ViewTypeDelegateAdapter.ANSWER_TYPE, AnswerDelegateAdapter())
        delegateAdapters.append(ViewTypeDelegateAdapter.HEADER_TYPE, HeaderCategoryDelegateAdapter())

        items = LinkedList()
        itemsSearchingData = mutableListOf()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            delegateAdapters.get(viewType).onCreateViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        this.delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, this.itemsSearchingData[position])
    }

    override fun getItemViewType(position: Int): Int = this.itemsSearchingData[position].getViewType()

    override fun getItemCount(): Int = itemsSearchingData.size

    private fun addAnswers(answers : LinkedList<ViewType>) {
        this.items.addAll(answers)
        this.itemsSearchingData.addAll(answers)
        notifyDataSetChanged()
    }

    fun clearAndAddAnswers(answers : List<Answer>) {
        this.items.clear()
        val groupedAnswers = groupAnswersByCategory(answers)
        addAnswers(groupedAnswers)
    }

    private fun groupAnswersByCategory(answers : List<Answer>) : LinkedList<ViewType> {
        val groupedList = LinkedList<ViewType>()
        val answersByCategory = HashMap<String, MutableList<Answer>>()
        answers.forEach { answer ->
            if (answersByCategory.containsKey(answer.category)) {
                answersByCategory[answer.category]?.add(answer)
            } else {
                val answersCategory = mutableListOf<Answer>()
                answersCategory.add(answer)
                answersByCategory.put(answer.category, answersCategory)
            }
        }
        addItemsGroupedByCategory(answersByCategory, groupedList, CategoryType.HARD_FACT)
        addItemsGroupedByCategory(answersByCategory, groupedList, CategoryType.LIFESTYLE)
        addItemsGroupedByCategory(answersByCategory, groupedList, CategoryType.INTROVERSION)
        addItemsGroupedByCategory(answersByCategory, groupedList, CategoryType.PASSION)

        return groupedList
    }

    private fun addItemsGroupedByCategory(answersByCategory : HashMap<String, MutableList<Answer>>, groupedList: LinkedList<ViewType>,
                               categoryType: CategoryType) {
        if (answersByCategory.containsKey(categoryType.category)) {
            groupedList.add(Category(categoryType.categoryName))
            groupedList.addAll(answersByCategory[categoryType.category]!!)
        }
    }

    private fun getAnswerItems() : List<Answer> {
        return items
                .filter { it.getViewType() == ViewTypeDelegateAdapter.ANSWER_TYPE }
                .map { it as Answer }
    }

    override fun getFilter(): Filter = queryFilter

    private inner class ItemFilter : Filter() {
        override fun performFiltering(query: CharSequence): Filter.FilterResults {
            val results = Filter.FilterResults()

            if (query.isBlank()) {
                results.values = items
                results.count = items.count()
                return results
            }

            val filterString = query.toString().toLowerCase()

            val filterList = getAnswerItems().filter { answer ->
                answer.question.contains(filterString, true)
                        || answer.answer.contains(filterString, true)}

            results.values = filterList
            results.count = filterList.count()

            return results
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
            itemsSearchingData = results.values as MutableList<ViewType>
            notifyDataSetChanged()
        }
    }
}