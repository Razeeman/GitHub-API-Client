package com.example.showcase.githubapi.di

import com.example.showcase.githubapi.ui.search.SearchActivity
import dagger.Subcomponent

/**
 * Component for activity level dependencies.
 */
@ActivityScoped
@Subcomponent(modules = [SearchModule::class])
interface SearchComponent {

    fun inject(activity: SearchActivity)

}