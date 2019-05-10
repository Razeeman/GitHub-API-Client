package com.razeeman.showcase.githubapi.ui.repos

import com.razeeman.showcase.githubapi.data.repo.BaseRepository
import com.razeeman.showcase.githubapi.di.ActivityScoped
import com.razeeman.showcase.githubapi.ui.model.RepoItem
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

/**
 * ViewModel for repository view.
 */
@ActivityScoped
class ReposViewModel
@Inject constructor(private val repository: BaseRepository)
    : BaseReposViewModel {

    private val loadingIndicatorSubject = BehaviorSubject.createDefault(false)

    override fun getRepos(query: String): Observable<List<RepoItem>> {
        return repository.findRepositories(query)
            .doOnSubscribe { loadingIndicatorSubject.onNext(true) }
            .doOnNext { loadingIndicatorSubject.onNext(false) }
            .map {
                it.map { repository -> RepoItem.fromRepository(repository) }
            }
    }

    override fun getLoadingVisibility(): Observable<Boolean> {
        return loadingIndicatorSubject
    }

    override fun refreshRepos(query: String): Completable {
        loadingIndicatorSubject.onNext(false)
        return Completable.complete()
    }
}