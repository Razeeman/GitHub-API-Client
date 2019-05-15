package com.razeeman.showcase.githubapi.ui.search

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.razeeman.showcase.githubapi.App
import com.razeeman.showcase.githubapi.R
import com.razeeman.showcase.githubapi.ui.RepoAdapter
import com.razeeman.showcase.githubapi.ui.model.RepoItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

/**
 * Repository search view.
 */
class SearchActivity : AppCompatActivity() {

    companion object {
        private const val BASE_QUERY_STATE_KEY = "base_query_state_key"
        private const val TAG = "CUSTOM TAG"
    }

    @Inject
    lateinit var searchViewModel: BaseSearchViewModel

    private var compositeDisposable = CompositeDisposable()
    private val repoAdapter = RepoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.getSearchComponent().inject(this)
        setContentView(R.layout.activity_main)

        // Setting up the toolbar.
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Setting up recycler view.
        main_items.apply {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(context)
            adapter = repoAdapter
        }

        setUpRefreshLayout()

        searchViewModel.searchQuery =
            savedInstanceState?.getString(BASE_QUERY_STATE_KEY) ?: searchViewModel.searchQuery

        searchViewModel.getRepos()
    }

    override fun onResume() {
        super.onResume()
        bindViewModel()
    }

    override fun onPause() {
        unbindViewModel()
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(BASE_QUERY_STATE_KEY, searchViewModel.searchQuery)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        App.releaseSearchComponent()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        setUpSearchView(menu?.findItem(R.id.action_search))

        return true
    }

    private fun setUpSearchView(searchItem: MenuItem?) {
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query.isNotEmpty()) {
                    refreshData(query)
                    searchItem.collapseActionView()
                }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun setUpRefreshLayout() {
        refresh_layout.apply {
            setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent)
            setOnRefreshListener {
                refreshData()
            }
        }
    }

    private fun bindViewModel() {
        compositeDisposable = CompositeDisposable()

        compositeDisposable.add(searchViewModel.getReposSubject()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { showItems(it) },
                { showError(it.message) }
            ))

        compositeDisposable.add(searchViewModel.getLoadingIndicatorSubject()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { setLoadingVisibility(it) },
                { Log.d(TAG, "Error showing loading indicator", it) }
            ))
    }

    private fun unbindViewModel() {
        compositeDisposable.dispose()
    }

    private fun refreshData(query: String? = null) {
        compositeDisposable.add(searchViewModel.refreshRepos(query)
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
