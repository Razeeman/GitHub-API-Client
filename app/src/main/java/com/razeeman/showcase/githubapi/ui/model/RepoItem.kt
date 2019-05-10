package com.razeeman.showcase.githubapi.ui.model

import com.razeeman.showcase.githubapi.data.api.model.Repository

/**
 * Model class to represent repository shown in ui.
 */
data class RepoItem (

    val id : Int,
    val name : String,
    val html_url : String,
    val description : String,
    val url : String,
    val stargazers_count : Int,
    val language : String?,
    val forks_count : Int

) {

    companion object {

        fun fromRepository(repository: Repository): RepoItem {
            return RepoItem(
                repository.id,
                repository.name,
                repository.html_url,
                repository.description,
                repository.url,
                repository.stargazers_count,
                repository.language,
                repository.forks_count)
        }

    }

}