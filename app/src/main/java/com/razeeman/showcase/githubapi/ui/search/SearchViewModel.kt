package com.razeeman.showcase.githubapi.ui.search

import com.razeeman.showcase.githubapi.data.repo.BaseRepository
import com.razeeman.showcase.githubapi.di.ActivityScoped
import com.razeeman.showcase.githubapi.ui.model.RepoItem
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

/**
 * ViewModel for repository search view.
 */
@ActivityScoped
class SearchViewModel
@Inject constructor(private val repository: BaseRepository)
    : BaseSearchViewModel {

    private val reposSubject = BehaviorSubject.create<List<RepoItem>>()
    private val loadingIndicatorSubject = BehaviorSubject.createDefault(false)

    override fun getReposSubject(): Observable<List<RepoItem>> {
        return reposSubject
    }

    override fun getLoadingIndicatorSubject(): Observable<Boolean> {
        return loadingIndicatorSubject
    }

    override fun getRepos(query: String) {
        repository.getRepos(query)
            .observeOn(Schedulers.computation())
            .map {
                it.map { repoDb -> RepoItem.fromRepoDb(repoDb) }
            }
            .doOnSubscribe { loadingIndicatorSubject.onNext(true) }
            .doOnSuccess { reposSubject.onNext(it) }
            .doFinally { loadingIndicatorSubject.onNext(false) }
            .subscribe()
    }

    override fun refreshRepos(query: String): Completable {
        return repository.refreshRepos(query)
            .doOnSubscribe { loadingIndicatorSubject.onNext(true) }
            .doFinally { getRepos(query) }
    }

}