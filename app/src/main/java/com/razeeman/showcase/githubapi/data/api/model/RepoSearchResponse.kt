package com.razeeman.showcase.githubapi.data.api.model

import com.google.gson.annotations.SerializedName

/**
 * Data class for converting Json response to search query.
 */
data class RepoSearchResponse (

    @SerializedName("total_count") val total_count : Int,
    @SerializedName("incomplete_results") val incomplete_results : Boolean,
    @SerializedName("items") val items : List<RepoApi>

)