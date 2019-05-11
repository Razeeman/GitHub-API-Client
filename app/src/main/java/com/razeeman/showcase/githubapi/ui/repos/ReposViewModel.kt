package com.razeeman.showcase.githubapi.ui.repos

import com.razeeman.showcase.githubapi.data.repo.BaseRepository
import com.razeeman.showcase.githubapi.di.ActivityScoped
import com.razeeman.showcase.githubapi.ui.model.RepoItem
import io.reactivex.Completable
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
        repository.getRepos(query)
            .subscribeOn(Schedulers.io())
            .map {
                it.map { repo -> RepoItem.fromDbRep(repo) }
            }
            .doOnSubscribe { loadingIndicatorSubject.onNext(true) }
            .doOnSuccess { reposSubject.onNext(it) }
            .doFinally { loadingIndicatorSubject.onNext(false) }
            .subscribe()
    }

    override fun refreshRepos(query: String): Completable {
        return repository.refreshRepos(query)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { loadingIndicatorSubject.onNext(true) }
            .doFinally { getRepos(query) }
    }

}