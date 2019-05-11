package com.razeeman.showcase.githubapi.data.repo

import com.razeeman.showcase.githubapi.data.db.RepoDb
import com.razeeman.showcase.githubapi.data.db.RepoDbDao
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Implementation of the repository that works with local data.
 */
class LocalRepository
private constructor(private val repositoryDao: RepoDbDao) {

    companion object {

        // RepoDb singleton instance.
        @Volatile private var INSTANCE: LocalRepository? = null

        /**
         * Provides repository instance.
         *
         * @param repositoryDao dao to access database.
         */
        fun get(repositoryDao: RepoDbDao): LocalRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: LocalRepository(repositoryDao).also { INSTANCE = it }
            }
        }
    }

    fun saveAll(repositories: List<RepoDb>): Completable {
        return Completable.fromCallable { repositoryDao.insertInTx(repositories) }
    }

    fun getAll(): Single<List<RepoDb>> {
        return Single.fromCallable { repositoryDao.loadAll() }
    }

    fun deleteAll(): Completable {
        return Completable.fromCallable { repositoryDao.deleteAll() }
    }

}