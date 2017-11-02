package com.profilizer.personalitytest.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.profilizer.common.ViewType
import com.profilizer.common.ViewTypeDelegateAdapter
import com.profilizer.personalitytest.model.PersonalityTest

class LatestTestsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: ArrayList<ViewType>
    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()

    init {
        delegateAdapters.append(ViewTypeDelegateAdapter.LATEST_TESTS, LatestTestsDelegateAdapter())

        items = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            delegateAdapters.get(viewType).onCreateViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        this.delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, this.items[position])
    }

    override fun getItemViewType(position: Int): Int = this.items[position].getViewType()

    override fun getItemCount(): Int = items.size

    private fun addTests(tests : Collection<PersonalityTest>) {
        this.items.addAll(tests)
        notifyDataSetChanged()
    }

    fun clearAndAddTests(tests : List<PersonalityTest>) {
        this.items.clear()
        addTests(tests)
    }

    fun getItemAt(position: Int) : PersonalityTest = this.items[position] as PersonalityTest

    fun getItems() : List<PersonalityTest> {
        return items
                .filter { it.getViewType() == ViewTypeDelegateAdapter.LATEST_TESTS }
                .map { it as PersonalityTest }
    }
}