package com.razeeman.showcase.githubapi.data.repo

import com.razeeman.showcase.githubapi.data.api.ApiService
import com.razeeman.showcase.githubapi.data.api.model.Repository
import io.reactivex.Observable

/**
 * Implementation of the repository that works with remote data.
 */
class RemoteRepository
private constructor(private val apiService: ApiService)
    : BaseRepository {

    companion object {

        // Repository singleton instance.
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

    override fun findRepositories(query: String): Observable<List<Repository>> {
        return apiService.findRepositories(query)
            .map { it.items }
    }

}