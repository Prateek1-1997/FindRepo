package com.example.teachmintapplication.domain.models

import com.google.gson.annotations.SerializedName

data class ContributorsListItemDto(
    @SerializedName("login") val login: String
)