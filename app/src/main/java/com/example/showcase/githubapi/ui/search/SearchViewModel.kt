package com.example.showcase.githubapi.ui.search

import android.util.Log
import com.example.showcase.githubapi.data.repo.BaseRepository
import com.example.showcase.githubapi.di.ActivityScoped
import com.example.showcase.githubapi.ui.model.RepoItem
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
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

    companion object {
        private const val TAG = "CUSTOM TAG"
    }

    override var searchQuery = "github api client"

    private val reposSubject = BehaviorSubject.create<List<RepoItem>>()
    private val loadingIndicatorSubject = BehaviorSubject.createDefault(false)
    private var compositeDisposable = CompositeDisposable()

    override fun getReposSubject(): Observable<List<RepoItem>> {
        return reposSubject
    }

    override fun getLoadingIndicatorSubject(): Observable<Boolean> {
        return loadingIndicatorSubject
    }

    override fun getRepos() {
        compositeDisposable.add(repository.getRepos(searchQuery)
            .observeOn(Schedulers.computation())
            .map {
                it.map { repoDb -> RepoItem.fromRepoDb(repoDb) }
            }
            .doOnSubscribe { loadingIndicatorSubject.onNext(true) }
            .doFinally { loadingIndicatorSubject.onNext(false) }
            .subscribe(
                { reposSubject.onNext(it) },
                { Log.d(TAG, "Error loading items from the repository", it) }
            ))
    }

    override fun refreshRepos(query: String?) {
        if (query != null) searchQuery = query

        compositeDisposable.add(repository.refreshRepos(searchQuery)
            .doOnSubscribe { loadingIndicatorSubject.onNext(true) }
            .doFinally { getRepos() }
            .subscribe(
                {},
                { Log.d(TAG, "Error refreshing items in the repository", it) }
            ))
    }

    override fun clear() {
        compositeDisposable.dispose()
    }
}