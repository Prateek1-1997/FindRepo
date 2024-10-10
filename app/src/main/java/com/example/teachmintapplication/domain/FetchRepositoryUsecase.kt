package com.example.teachmintapplication.domain

import android.content.ContentValues.TAG
import android.util.Log
import com.example.teachmintapplication.data.NetworkManager
import javax.inject.Inject

class FetchRepositoryUsecase @Inject constructor(
    private val localRepo: ILocalRepo,
    private val iRepository: IRepository,
    private val networkManager: NetworkManager
) {

    suspend fun invoke(query: String,page:Int) : List<Item>?{
        return if (networkManager.isNetworkAvailable()) {
            iRepository.getRepoList(query,page)
        } else {
            localRepo.getAllRepositories().orEmpty().map{
                Item(it.fullName, Owner(it.avatarUrl))
            }
        }
    }
}