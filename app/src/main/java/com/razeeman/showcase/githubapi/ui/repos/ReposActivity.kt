package com.razeeman.showcase.githubapi.ui.repos

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.razeeman.showcase.githubapi.R
import com.razeeman.showcase.githubapi.data.api.ApiService
import com.razeeman.showcase.githubapi.data.repo.RemoteRepository
import com.razeeman.showcase.githubapi.ui.RepoAdapter
import com.razeeman.showcase.githubapi.ui.model.RepoItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Repository list view.
 */
class ReposActivity : AppCompatActivity() {

    private lateinit var reposViewModel: BaseReposViewModel

    private val compositeDisposable = CompositeDisposable()
    private val repoAdapter = RepoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_items.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = repoAdapter
        }

        val baseUrl = "https://api.github.com"
        val baseQuery = "test"

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)
        val repository = RemoteRepository.get(apiService)
        reposViewModel = ReposViewModel(repository)

        val disposable = reposViewModel.getRepos(baseQuery)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { showItems(it) },
                { showError(it.message) }
            )

        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
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
