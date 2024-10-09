package com.example.teachmintapplication.domain

data class RepositoryDto(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)