package com.razeeman.showcase.githubapi.ui.repos

import com.razeeman.showcase.githubapi.ui.model.RepoItem
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Contract for ViewModel.
 */
interface BaseReposViewModel {

    fun getRepos(query: String): Observable<List<RepoItem>>

    fun getLoadingVisibility(): Observable<Boolean>

    fun refreshRepos(query: String): Completable

}