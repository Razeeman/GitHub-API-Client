package com.razeeman.showcase.githubapi.ui.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.razeeman.showcase.githubapi.App
import com.razeeman.showcase.githubapi.R
import com.razeeman.showcase.githubapi.ui.RepoAdapter
import com.razeeman.showcase.githubapi.ui.model.RepoItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

/**
 * Repository search view.
 */
class SearchActivity : AppCompatActivity() {

    companion object {

        const val TAG = "custom tag"
        const val BASE_QUERY = "test"

    }

    @Inject
    lateinit var searchViewModel: BaseSearchViewModel

    private var compositeDisposable = CompositeDisposable()
    private val repoAdapter = RepoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.getSearchComponent().inject(this)
        setContentView(R.layout.activity_main)

        main_items.apply {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(context)
            adapter = repoAdapter
        }

        setUpRefreshLayout()

        refreshData(BASE_QUERY)
    }

    override fun onResume() {
        super.onResume()
        bindViewModel()
    }

    override fun onPause() {
        unbindViewModel()
        super.onPause()
    }

    override fun onDestroy() {
        App.releaseSearchComponent()
        super.onDestroy()
    }

    private fun setUpRefreshLayout() {
        refresh_layout.apply {
            setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent)
            setOnRefreshListener {
                refreshData(BASE_QUERY)
            }
        }
    }

    private fun bindViewModel() {
        compositeDisposable = CompositeDisposable()

        compositeDisposable.add(searchViewModel.getReposSubject()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { showItems(it) },
                { showError(it.message) }
            ))

        compositeDisposable.add(searchViewModel.getLoadingIndicatorSubject()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { setLoadingVisibility(it) },
                { Log.d(TAG, "Error showing loading indicator", it) }
            ))

        searchViewModel.getRepos(BASE_QUERY)
    }

    private fun unbindViewModel() {
        compositeDisposable.dispose()
    }

    private fun refreshData(query: String) {
        compositeDisposable.add(searchViewModel.refreshRepos(query)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe())
    }

    private fun showItems(items: List<RepoItem>) {
        main_items.visibility = View.VISIBLE
        main_error.visibility = View.INVISIBLE

        repoAdapter.replaceItems(items)
    }

    private fun showError(message: String?) {
        main_items.visibility = View.INVISIBLE
        main_error.visibility = View.VISIBLE

        main_error.text = message
    }

    private fun setLoadingVisibility(visibility: Boolean) {
        refresh_layout.isRefreshing = visibility
    }

}
