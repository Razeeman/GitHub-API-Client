package com.example.showcase.githubapi.ui.search

import com.example.showcase.githubapi.ui.model.RepoItem
import io.reactivex.Observable

/**
 * Contract for ViewModel.
 */
interface BaseSearchViewModel {

    var searchQuery: String

    fun getReposSubject(): Observable<List<RepoItem>>

    fun getLoadingIndicatorSubject(): Observable<Boolean>

    fun getRepos()

    fun refreshRepos(query: String? = null)

    fun clear()

}