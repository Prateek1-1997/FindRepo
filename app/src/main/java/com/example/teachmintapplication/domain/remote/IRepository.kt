package com.example.teachmintapplication.domain.remote

import com.example.teachmintapplication.domain.models.RepositoryItem
import com.example.teachmintapplication.domain.models.RepositoryDetailDto

interface IRepository {

    suspend fun getRepoList(query: String,page:Int) : List<RepositoryItem>?

    suspend fun getRepoDetails(repoName:String) : RepositoryDetailDto?
}