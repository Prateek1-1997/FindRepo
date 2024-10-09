package com.example.teachmintapplication.domain

import com.example.teachmintapplication.data.GithubService
import okhttp3.Response
import retrofit2.http.Query
import javax.inject.Inject

interface IRepository {

    suspend fun getRepoList(query: String,page:Int) : List<Item>?
}