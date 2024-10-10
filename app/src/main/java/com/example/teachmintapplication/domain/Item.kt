package com.example.teachmintapplication.domain


import com.google.gson.annotations.SerializedName


data class Item(
    @SerializedName("full_name") val fullName: String,
    @SerializedName("owner") val owner: Owner
)

