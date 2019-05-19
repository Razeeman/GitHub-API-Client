package com.example.showcase.githubapi.di

import com.example.showcase.githubapi.ui.search.BaseSearchViewModel
import com.example.showcase.githubapi.ui.search.SearchViewModel
import dagger.Module
import dagger.Provides

/**
 * Module for app level dependencies.
 */
@Module
class SearchModule {

    @Provides
    @ActivityScoped
    fun getBaseSearchViewModel(searchViewModel: SearchViewModel): BaseSearchViewModel {
        return searchViewModel
    }

}