package com.example.teachmintapplication.domain

import com.google.gson.annotations.SerializedName

data class RepoDetailDto(
    @SerializedName("full_name") val fullName: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("owner") val owner: Owner,
    @SerializedName("html_url") val htmlUrl: String?,
    @SerializedName("contributors") val contributors: List<ContributorsListDtoItem>? = emptyList()
)