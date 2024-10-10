package com.example.teachmintapplication.domain.remote

import com.example.teachmintapplication.domain.models.RepositoryItem
import com.example.teachmintapplication.domain.models.RepositoryDetailDto

interface IRepository {

    suspend fun getRepositoriesList(query: String, page:Int) : List<RepositoryItem>?

    suspend fun getRepositoryDetails(repoName:String) : RepositoryDetailDto?
}