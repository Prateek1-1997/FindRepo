package com.example.teachmintapplication.domain.models


import com.google.gson.annotations.SerializedName


data class RepositoryItem(
    @SerializedName("name") val fullName: String,
    @SerializedName("owner") val owner: Owner
)

data class Owner(
    @SerializedName("avatar_url") val avatarUrl: String?
)

