package com.example.teachmintapplication.data

import com.example.teachmintapplication.domain.IRepository
import com.example.teachmintapplication.domain.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.http.Query
import javax.inject.Inject

class RepoImpl @Inject constructor(val githubService: GithubService) : IRepository{
    override suspend fun getRepoList(query: String,page:Int): List<Item>? {
        return withContext(Dispatchers.IO){
            val response = githubService.getRepoList(query, page = page)
            if(response.isSuccessful){
                response.body()?.items
            }else{
                emptyList()
            }
        }
    }


}