package com.razeeman.showcase.githubapi.ui.repos

import com.razeeman.showcase.githubapi.data.repo.BaseRepository
import com.razeeman.showcase.githubapi.di.ActivityScoped
import com.razeeman.showcase.githubapi.ui.model.RepoItem
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

/**
 * ViewModel for repository view.
 */
@ActivityScoped
class ReposViewModel
@Inject constructor(private val repository: BaseRepository)
    : BaseReposViewModel {

    private val reposSubject = BehaviorSubject.create<List<RepoItem>>()
    private val loadingIndicatorSubject = BehaviorSubject.createDefault(false)

    override fun getReposSubject(): Observable<List<RepoItem>> {
        return reposSubject
    }

    override fun getLoadingIndicatorSubject(): Observable<Boolean> {
        return loadingIndicatorSubject
    }

    override fun getRepos(query: String) {
        repository.findRepositories(query)
            .subscribeOn(Schedulers.io())
            .map {
                it.map { repository -> RepoItem.fromRepository(repository) }
            }
            .doOnSubscribe { loadingIndicatorSubject.onNext(true) }
            .doOnSuccess { reposSubject.onNext(it) }
            .doFinally { loadingIndicatorSubject.onNext(false) }
            .subscribe()
    }

}