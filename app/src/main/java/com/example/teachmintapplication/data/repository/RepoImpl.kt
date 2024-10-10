package com.example.teachmintapplication.data.repository

import com.abhisek.project.bookshelf.data.local.dao.LocalRepoDao
import com.example.teachmintapplication.data.local.entity.ItemEntity
import com.example.teachmintapplication.data.remote.service.GithubService
import com.example.teachmintapplication.domain.remote.IRepository
import com.example.teachmintapplication.domain.models.RepositoryItem
import com.example.teachmintapplication.domain.models.RepositoryDetailDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepoImpl @Inject constructor(private val githubService: GithubService, private val repositoriesDao:LocalRepoDao) :
    IRepository {
    override suspend fun getRepoList(query: String,page:Int): List<RepositoryItem>? {
        return withContext(Dispatchers.IO){
            val response = githubService.getRepoList(query, page = page)
            if(response.isSuccessful){
                repositoriesDao.insertRepositories(response.body()?.items?.subList(0,15).orEmpty().map { ItemEntity(fullName = it.fullName, avatarUrl = it.owner.avatarUrl.orEmpty()) })
                response.body()?.items
            }else{
                emptyList()
            }
        }
    }

    override suspend fun getRepoDetails(repoName:String): RepositoryDetailDto? {
        return withContext(Dispatchers.IO){
            val response1 = async {  githubService.getRepoDetails(repoName)}
            val response2 = async {  githubService.getRepoContributors(repoName)}

            val result1 = response1.await()
            val result2 = response2.await()



            if(result1.isSuccessful){
                if(result2.isSuccessful){
                  val repoDetails = result1.body()
                    repoDetails?.copy(contributors = result2.body())

                }else{
                    result1.body()
                }
            }else{
                null
            }
        }
    }


}