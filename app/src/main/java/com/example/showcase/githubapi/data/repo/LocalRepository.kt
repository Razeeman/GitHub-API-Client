package com.example.showcase.githubapi.data.repo

import com.example.showcase.githubapi.data.db.RepoDb
import com.example.showcase.githubapi.data.db.RepoDbDao
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

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
            .subscribeOn(Schedulers.io())
    }

    fun getAll(): Single<List<RepoDb>> {
        return Single.fromCallable { dao.loadAll() }
            .subscribeOn(Schedulers.io())
    }

    fun deleteAll(): Completable {
        return Completable.fromCallable { dao.deleteAll() }
            .subscribeOn(Schedulers.io())
    }

}