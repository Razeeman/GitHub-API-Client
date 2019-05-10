package com.razeeman.showcase.githubapi.ui.repos

import com.razeeman.showcase.githubapi.ui.model.RepoItem
import io.reactivex.Observable

/**
 * Contract for ViewModel.
 */
interface BaseReposViewModel {

    fun getReposSubject(): Observable<List<RepoItem>>

    fun getLoadingIndicatorSubject(): Observable<Boolean>

    fun getRepos(query: String)

}