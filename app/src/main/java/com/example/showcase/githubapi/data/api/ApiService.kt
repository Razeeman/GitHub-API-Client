package com.example.showcase.githubapi.data.api

import com.example.showcase.githubapi.data.api.model.RepoSearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search/repositories")
    fun findRepos(@Query("q") query: String): Single<RepoSearchResponse>

}