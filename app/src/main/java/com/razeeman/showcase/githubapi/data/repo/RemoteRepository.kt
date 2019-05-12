package com.razeeman.showcase.githubapi.data.repo

import com.razeeman.showcase.githubapi.data.api.ApiService
import com.razeeman.showcase.githubapi.data.api.model.RepoApi
import io.reactivex.Single

/**
 * Implementation of the repository that works with remote data.
 */
class RemoteRepository
private constructor(private val api: ApiService) {

    companion object {

        // Repository singleton instance.
        @Volatile private var INSTANCE: RemoteRepository? = null

        /**
         * Provides repository instance.
         *
         * @param api service to provide remote data.
         */
        fun get(api: ApiService): RemoteRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: RemoteRepository(api).also { INSTANCE = it }
            }
        }
    }

    fun getAll(query: String): Single<List<RepoApi>> {
        return api.findRepos(query)
            .map { it.items }
    }

}