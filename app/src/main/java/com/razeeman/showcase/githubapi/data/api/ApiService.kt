package com.razeeman.showcase.githubapi.data.api

import com.razeeman.showcase.githubapi.data.api.model.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search/repositories")
    fun findRepositories(@Query("q") query: String): Call<SearchResponse>

}