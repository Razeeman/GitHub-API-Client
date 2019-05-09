package com.razeeman.showcase.githubapi.di

import com.razeeman.showcase.githubapi.ui.repos.ReposActivity
import dagger.Subcomponent

/**
 * Component for activity level dependencies.
 */
@ActivityScoped
@Subcomponent(modules = [ReposModule::class])
interface ReposComponent {

    fun inject(activity: ReposActivity)

}