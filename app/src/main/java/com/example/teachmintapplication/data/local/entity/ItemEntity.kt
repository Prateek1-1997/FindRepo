package com.example.teachmintapplication.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repositories")
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int =0,
    val fullName: String,
    val avatarUrl: String,
)
