package com.example.teachmintapplication.domain.models

import com.google.gson.annotations.SerializedName

data class RepositoryDetailDto(
    @SerializedName("full_name") val fullName: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("owner") val owner: Owner,
    @SerializedName("html_url") val htmlUrl: String?,
    @SerializedName("contributors") val contributors: List<ContributorsListItemDto>? = emptyList()
)