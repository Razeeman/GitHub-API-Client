package com.razeeman.showcase.githubapi.ui.repos

import com.razeeman.showcase.githubapi.data.repo.BaseRepository
import com.razeeman.showcase.githubapi.di.ActivityScoped
import com.razeeman.showcase.githubapi.ui.model.RepoItem
import io.reactivex.Observable
import javax.inject.Inject

/**
 * ViewModel for repository view.
 */
@ActivityScoped
class ReposViewModel
@Inject constructor(private val repository: BaseRepository)
    : BaseReposViewModel {

    override fun getRepos(query: String): Observable<List<RepoItem>> {
        return repository.findRepositories(query).map {
            it.map { repository -> RepoItem.fromRepository(repository) }
        }
    }
}