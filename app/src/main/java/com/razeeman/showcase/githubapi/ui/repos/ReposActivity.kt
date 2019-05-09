package com.razeeman.showcase.githubapi.ui.repos

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
 * Repository list view.
 */
class ReposActivity : AppCompatActivity() {

    companion object {

        const val BASE_QUERY = "test"

    }

    @Inject
    lateinit var reposViewModel: BaseReposViewModel

    private var compositeDisposable = CompositeDisposable()
    private val repoAdapter = RepoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.getReposComponent().inject(this)
        setContentView(R.layout.activity_main)

        main_items.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = repoAdapter
        }

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
        App.releaseReposComponent()
        super.onDestroy()
    }

    private fun bindViewModel() {
        compositeDisposable = CompositeDisposable()

        val disposable = reposViewModel.getRepos(BASE_QUERY)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { showItems(it) },
                { showError(it.message) }
            )

        compositeDisposable.add(disposable)
    }

    private fun unbindViewModel() {
        compositeDisposable.dispose()
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
}
