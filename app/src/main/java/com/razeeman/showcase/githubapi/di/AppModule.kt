package com.razeeman.showcase.githubapi.di

import android.content.Context
import com.razeeman.showcase.githubapi.App
import com.razeeman.showcase.githubapi.data.api.ApiService
import com.razeeman.showcase.githubapi.data.repo.BaseRepository
import com.razeeman.showcase.githubapi.data.repo.RemoteRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * Module for app level dependencies.
 */
@Module
class AppModule(application: App) {

    private var appContext: Context = application.applicationContext

    @Provides
    @Singleton
    @Named("AppContext")
    fun getAppContext(): Context {
        return appContext
    }

    @Provides
    @Singleton
    @Named("BaseUrl")
    fun getBaseUrl(): String {
        return "https://api.github.com"
    }

    @Provides
    @Singleton
    fun getRetrofitInstance(@Named("BaseUrl") baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun getApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun getBaseRepository(apiService: ApiService): BaseRepository {
        return RemoteRepository.get(apiService)
    }

}