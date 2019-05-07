package com.razeeman.showcase.githubapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.razeeman.showcase.githubapi.api.ApiService
import com.razeeman.showcase.githubapi.api.model.SearchResponse
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val baseUrl = "https://api.github.com"
        val baseQuery = "test"

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.findRepositories(baseQuery)

        call.enqueue(object : Callback<SearchResponse> {
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                response_text.text = t.message
            }
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                for (repository in response.body()!!.items) {
                    response_text.text = "${response_text.text}${repository.name}\n"
                }
            }
        })
    }
}
