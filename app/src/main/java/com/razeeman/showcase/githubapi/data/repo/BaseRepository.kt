package com.razeeman.showcase.githubapi.data.repo

import com.razeeman.showcase.githubapi.data.db.RepoDb
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Repository contract.
 */
interface BaseRepository {

    fun getRepos(query: String): Single<List<RepoDb>>

    fun refreshRepos(query: String): Completable

}