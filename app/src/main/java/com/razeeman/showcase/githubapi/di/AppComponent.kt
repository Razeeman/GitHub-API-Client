package com.razeeman.showcase.githubapi.di

import dagger.Component
import javax.inject.Singleton

/**
 * Component for app level dependencies.
 */
@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun plusReposComponent(reposModule: ReposModule): ReposComponent

}