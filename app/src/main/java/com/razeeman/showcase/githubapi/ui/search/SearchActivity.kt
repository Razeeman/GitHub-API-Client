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
import kotlinx.android.synthetic.main.activity_search.*
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
        setContentView(R.layout.activity_search)

        // Setting up the toolbar.
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Setting up recycler view.
        search_items.apply {
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

    private fun setUpSearchView(searchMenuItem: MenuItem?) {
        val searchMenuView = searchMenuItem?.actionView as SearchView
        searchMenuView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query.isNotEmpty()) {
                    searchViewModel.refreshRepos(query)
                    searchMenuItem.collapseActionView()
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
                searchViewModel.refreshRepos()
            }
        }
    }

    private fun bindViewModel() {
        compositeDisposable = CompositeDisposable()

        compositeDisposable.add(searchViewModel.getReposSubject()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { if (it.isEmpty()) showMessage("No results") else showItems(it) },
                { Log.d(TAG, "Error showing items", it) }
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
        searchViewModel.clear()
    }

    private fun showItems(items: List<RepoItem>) {
        search_items.visibility = View.VISIBLE
        search_message.visibility = View.INVISIBLE

        repoAdapter.replaceItems(items)
    }

    private fun showMessage(message: String?) {
        search_items.visibility = View.INVISIBLE
        search_message.visibility = View.VISIBLE

        search_message.text = message
    }

    private fun setLoadingVisibility(visibility: Boolean) {
        refresh_layout.isRefreshing = visibility
    }

}
