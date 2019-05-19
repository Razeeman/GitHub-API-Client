package com.example.showcase.githubapi.di

import dagger.Component
import javax.inject.Singleton

/**
 * Component for app level dependencies.
 */
@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun plusSearchComponent(searchModule: SearchModule): SearchComponent

}