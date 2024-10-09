package com.example.teachmintapplication.data

import com.example.teachmintapplication.domain.RepositoryDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    @GET("search/repositories")
    suspend fun getRepoList(@Query ("q") query: String, @Query("per_page") perPage : Int =10,@Query ("page") page:Int) : Response<RepositoryDto>
}