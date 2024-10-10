package com.example.teachmintapplication.domain

interface IRepository {

    suspend fun getRepoList(query: String,page:Int) : List<Item>?

    suspend fun getRepoDetails(repoName:String) : RepoDetailDto?
}