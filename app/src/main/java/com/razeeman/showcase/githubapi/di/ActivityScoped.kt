package com.razeeman.showcase.githubapi.di

import javax.inject.Scope

/**
 * Custom dagger scope used for activities.
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScoped