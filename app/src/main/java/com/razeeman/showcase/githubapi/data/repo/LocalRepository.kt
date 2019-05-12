package com.razeeman.showcase.githubapi.data.repo

import com.razeeman.showcase.githubapi.data.db.RepoDb
import com.razeeman.showcase.githubapi.data.db.RepoDbDao
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Implementation of the repository that works with local data.
 */
class LocalRepository
private constructor(private val dao: RepoDbDao) {

    companion object {

        // Repository singleton instance.
        @Volatile private var INSTANCE: LocalRepository? = null

        /**
         * Provides repository instance.
         *
         * @param dao dao to access database.
         */
        fun get(dao: RepoDbDao): LocalRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: LocalRepository(dao).also { INSTANCE = it }
            }
        }
    }

    fun saveAll(repositories: List<RepoDb>): Completable {
        return Completable.fromCallable { dao.insertInTx(repositories) }
    }

    fun getAll(): Single<List<RepoDb>> {
        return Single.fromCallable { dao.loadAll() }
    }

    fun deleteAll(): Completable {
        return Completable.fromCallable { dao.deleteAll() }
    }

}