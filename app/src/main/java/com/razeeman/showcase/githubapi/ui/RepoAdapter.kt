package com.razeeman.showcase.githubapi.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.razeeman.showcase.githubapi.ui.model.RepoItem

/**
 * Adapter for RecyclerView of [RepoItem]s.
 */
class RepoAdapter: RecyclerView.Adapter<RepoHolder>() {

    private var items: List<RepoItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RepoHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RepoHolder, position: Int) {
        holder.bind(items[position])
    }

    fun replaceItems(newItems: List<RepoItem>) {
        items = newItems
        notifyDataSetChanged()
    }

}