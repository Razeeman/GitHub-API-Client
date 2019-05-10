package com.razeeman.showcase.githubapi.data.api

import com.razeeman.showcase.githubapi.data.api.model.RepositorySearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search/repositories")
    fun findRepositories(@Query("q") query: String): Single<RepositorySearchResponse>

}