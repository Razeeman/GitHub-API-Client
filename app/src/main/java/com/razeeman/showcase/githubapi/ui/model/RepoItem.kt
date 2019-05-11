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

        fun fromApiRepo(apiRepo: RepoApi): RepoItem {
            return RepoItem(
                apiRepo.name,
                apiRepo.description,
                apiRepo.stargazers_count,
                apiRepo.language,
                apiRepo.forks_count)
        }

        fun fromDbRep(dbRepo: RepoDb): RepoItem {
            return RepoItem(
                dbRepo.name,
                dbRepo.description,
                dbRepo.stargazers_count,
                dbRepo.language,
                dbRepo.forks_count)
        }

    }

}