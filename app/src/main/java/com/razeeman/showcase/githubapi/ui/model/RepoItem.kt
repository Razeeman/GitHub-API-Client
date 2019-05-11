package com.razeeman.showcase.githubapi.ui.model

import com.razeeman.showcase.githubapi.data.api.model.RepoApi
import com.razeeman.showcase.githubapi.data.db.RepoDb

/**
 * Model class to represent repository shown in ui.
 */
data class RepoItem (

    val name : String?,
    val description : String?,
    val stargazers_count : Int?,
    val language : String?,
    val forks_count : Int?

) {

    companion object {

        fun fromRepoApi(repoApi: RepoApi): RepoItem {
            return RepoItem(
                repoApi.name,
                repoApi.description,
                repoApi.stargazers_count,
                repoApi.language,
                repoApi.forks_count)
        }

        fun fromRepoDb(repoDb: RepoDb): RepoItem {
            return RepoItem(
                repoDb.name,
                repoDb.description,
                repoDb.stargazers_count,
                repoDb.language,
                repoDb.forks_count)
        }

    }

}