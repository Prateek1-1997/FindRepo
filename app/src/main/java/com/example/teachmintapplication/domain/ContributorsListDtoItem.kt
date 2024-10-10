package com.example.teachmintapplication.domain

import com.google.gson.annotations.SerializedName

data class ContributorsListDtoItem(
    @SerializedName("login") val login: String
)