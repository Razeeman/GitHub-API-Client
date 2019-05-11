package com.razeeman.showcase.githubapi.data.repo

import com.razeeman.showcase.githubapi.data.api.ApiService
import com.razeeman.showcase.githubapi.data.api.model.RepoApi
import io.reactivex.Single

/**
 * Implementation of the repository that works with remote data.
 */
class RemoteRepository
private constructor(private val apiService: ApiService) {

    companion object {

        // RepoDb singleton instance.
        @Volatile private var INSTANCE: RemoteRepository? = null

        /**
         * Provides repository instance.
         *
         * @param apiService service to provide remote data.
         */
        fun get(apiService: ApiService): RemoteRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: RemoteRepository(apiService).also { INSTANCE = it }
            }
        }
    }

    fun getAll(query: String): Single<List<RepoApi>> {
        return apiService.findRepositories(query)
            .map { it.items }
    }

}