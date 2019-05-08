package com.razeeman.showcase.githubapi.data.repo

import com.razeeman.showcase.githubapi.data.api.model.Repository
import io.reactivex.Observable

/**
 * Repository contract.
 */
interface BaseRepository {

    fun findRepositories(query: String): Observable<List<Repository>>

}