package com.razeeman.showcase.githubapi.data.repo

import com.razeeman.showcase.githubapi.data.db.RepoDb
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * Implementation of the main repository that works with local and remote data.
 */
class MainRepository
private constructor(private val localRepository: LocalRepository,
                    private val remoteRepository: RemoteRepository)
    : BaseRepository {

    companion object {

        // RepoDb singleton instance.
        @Volatile private var INSTANCE: MainRepository? = null

        /**
         * Provides repository instance.
         *
         * @param localRepository  to access local repository.
         * @param remoteRepository to access remote repository.
         */
        fun get(localRepository: LocalRepository, remoteRepository: RemoteRepository): MainRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: MainRepository(localRepository, remoteRepository).also { INSTANCE = it }
            }
        }
    }

    override fun getRepos(query: String): Single<List<RepoDb>> {
        return Single.concat(
            //Single.just(listOf(RepoDb().apply { name = "test" })),
            localRepository.getAll(),
            remoteRepository.getAll(query)
                .map {
                    it.map { apiRepo -> RepoDb.fromApiRepository(apiRepo) }
                })
            .filter { it.isNotEmpty() }
            .firstElement()
            .toSingle()
    }

    override fun refreshRepos(query: String): Completable {
        return remoteRepository.getAll(query)
            .subscribeOn(Schedulers.io())
            .doOnSuccess {
                localRepository.deleteAll().subscribe()
                localRepository.saveAll(it.map { repoApi -> RepoDb.fromApiRepository(repoApi) }).subscribe()
            }
            .ignoreElement()
    }
}