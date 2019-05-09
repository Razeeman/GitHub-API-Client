package com.razeeman.showcase.githubapi.di

import com.razeeman.showcase.githubapi.ui.repos.BaseReposViewModel
import com.razeeman.showcase.githubapi.ui.repos.ReposViewModel
import dagger.Module
import dagger.Provides

/**
 * Module for app level dependencies.
 */
@Module
class ReposModule {

    @Provides
    @ActivityScoped
    fun getBaseReposViewModel(reposViewModel: ReposViewModel): BaseReposViewModel {
        return reposViewModel
    }

}