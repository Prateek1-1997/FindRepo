package com.example.teachmintapplication.data.repository
import com.example.teachmintapplication.data.local.dao.LocalRepositoryDao
import com.example.teachmintapplication.data.local.entity.ItemEntity
import com.example.teachmintapplication.data.remote.service.GithubService
import com.example.teachmintapplication.domain.remote.IRepository
import com.example.teachmintapplication.domain.models.RepositoryItem
import com.example.teachmintapplication.domain.models.RepositoryDetailDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val githubService: GithubService,  private val repositoriesDao: LocalRepositoryDao) :
    IRepository {
    override suspend fun getRepositoriesList(query: String, page:Int): List<RepositoryItem>? {
        return withContext(Dispatchers.IO){
            val response = githubService.getRepositoriesList(query, page = page)
            if(response.isSuccessful){
                repositoriesDao.insertRepositories(response.body()?.items?.subList(0,15).orEmpty().map { ItemEntity(fullName = it.name, avatarUrl = it.owner.avatarUrl.orEmpty()) })
                response.body()?.items
            }else{
                emptyList()
            }
        }
    }


    override suspend fun getRepositoryDetails(repoName:String): RepositoryDetailDto? {
        return withContext(Dispatchers.IO){
            val response1 = async {  githubService.getRepositoryDetails(repoName)}
            val response2 = async {  githubService.getRepositoryContributors(repoName)}

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