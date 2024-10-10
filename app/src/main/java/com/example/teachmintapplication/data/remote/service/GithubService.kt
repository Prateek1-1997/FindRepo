package com.example.teachmintapplication.data.remote.service

import com.example.teachmintapplication.domain.models.ContributorsListDto
import com.example.teachmintapplication.domain.models.RepositoryDetailDto
import com.example.teachmintapplication.domain.models.RepositoriesListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("search/repositories")
    suspend fun getRepoList(@Query ("q") query: String, @Query("per_page") perPage : Int =15,@Query ("page") page:Int) : Response<RepositoriesListDto>

    @GET("repos/{repo_name}")
    suspend fun getRepoDetails(@Path("repo_name",encoded = true) repoName: String ): Response<RepositoryDetailDto>

    @GET("repos/{repo_name}/contributors")
    suspend fun getRepoContributors(@Path("repo_name",encoded = true) repoName: String ): Response<ContributorsListDto>
}