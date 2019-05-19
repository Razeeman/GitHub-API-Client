package com.example.showcase.githubapi.data.repo

import com.example.showcase.githubapi.data.db.RepoDb
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Implementation of the main repository that works with local and remote data.
 */
class MainRepository
private constructor(private val localRepository: LocalRepository,
                    private val remoteRepository: RemoteRepository)
    : BaseRepository {

    companion object {

        // Repository singleton instance.
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
                    it.map { repoApi -> RepoDb.fromRepoApi(repoApi) }
                })
            .filter { it.isNotEmpty() }
            .firstElement()
            .defaultIfEmpty(ArrayList())
            .toSingle()
    }

    override fun refreshRepos(query: String): Completable {
        return remoteRepository.getAll(query)
            .flatMapCompletable {
                localRepository.deleteAll()
                .andThen(localRepository.saveAll(it.map {
                        repoApi -> RepoDb.fromRepoApi(repoApi)
                }))
            }
    }
}