package com.razeeman.showcase.githubapi

import android.app.Application
import com.razeeman.showcase.githubapi.di.*

class App: Application() {

    companion object {

        private lateinit var appComponent: AppComponent
        private var reposComponent: ReposComponent? = null

        private fun getAppComponent(): AppComponent {
            return appComponent
        }

        fun getReposComponent(): ReposComponent {
            return reposComponent ?: getAppComponent().plusReposComponent(ReposModule()).also {
                reposComponent = it
            }
        }

        fun releaseReposComponent() {
            reposComponent = null
        }

    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}