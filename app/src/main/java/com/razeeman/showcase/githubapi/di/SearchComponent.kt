package com.razeeman.showcase.githubapi.di

import com.razeeman.showcase.githubapi.ui.search.SearchActivity
import dagger.Subcomponent

/**
 * Component for activity level dependencies.
 */
@ActivityScoped
@Subcomponent(modules = [SearchModule::class])
interface SearchComponent {

    fun inject(activity: SearchActivity)

}