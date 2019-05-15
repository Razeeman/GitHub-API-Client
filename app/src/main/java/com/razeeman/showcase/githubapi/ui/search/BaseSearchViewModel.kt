package com.razeeman.showcase.githubapi.ui.search

import com.razeeman.showcase.githubapi.ui.model.RepoItem
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Contract for ViewModel.
 */
interface BaseSearchViewModel {

    var searchQuery: String

    fun getReposSubject(): Observable<List<RepoItem>>

    fun getLoadingIndicatorSubject(): Observable<Boolean>

    fun getRepos()

    fun refreshRepos(query: String?): Completable

}