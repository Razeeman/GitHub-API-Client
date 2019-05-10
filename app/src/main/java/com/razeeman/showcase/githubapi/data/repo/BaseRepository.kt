package com.razeeman.showcase.githubapi.data.repo

import com.razeeman.showcase.githubapi.data.api.model.Repository
import io.reactivex.Single

/**
 * Repository contract.
 */
interface BaseRepository {

    fun findRepositories(query: String): Single<List<Repository>>

}