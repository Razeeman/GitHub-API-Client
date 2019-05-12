package com.razeeman.showcase.githubapi

import android.app.Application
import com.razeeman.showcase.githubapi.di.*

class App: Application() {

    companion object {

        private lateinit var appComponent: AppComponent
        private var searchComponent: SearchComponent? = null

        private fun getAppComponent(): AppComponent {
            return appComponent
        }

        fun getSearchComponent(): SearchComponent {
            return searchComponent ?: getAppComponent().plusSearchComponent(SearchModule()).also {
                searchComponent = it
            }
        }

        fun releaseSearchComponent() {
            searchComponent = null
        }

    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}