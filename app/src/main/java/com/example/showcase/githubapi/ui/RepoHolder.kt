package com.example.showcase.githubapi.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.showcase.githubapi.R
import com.example.showcase.githubapi.ui.model.RepoItem
import kotlinx.android.synthetic.main.repo_layout.view.*

/**
 * Holder for RecyclerView of [RepoItem]s.
 *
 * @param inflater layout inflater to use in this view.
 * @param parent   parent view.
 */
class RepoHolder(inflater: LayoutInflater, private val parent: ViewGroup)
    : RecyclerView.ViewHolder(inflater.inflate(R.layout.repo_layout, parent, false)) {

    fun bind(repoItem: RepoItem) {
        itemView.apply {
            repo_name.text = repoItem.name
            repo_description.text = repoItem.description
            repo_star_count.text = repoItem.stargazers_count.toString()
            repo_language.text = repoItem.language
        }
    }

}