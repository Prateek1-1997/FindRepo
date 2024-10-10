package com.example.teachmintapplication.domain.usecase

import com.example.teachmintapplication.data.remote.NetworkManager
import com.example.teachmintapplication.domain.local.ILocalRepository
import com.example.teachmintapplication.domain.models.Owner
import com.example.teachmintapplication.domain.remote.IRepository
import com.example.teachmintapplication.domain.models.RepositoryItem
import javax.inject.Inject

class FetchRepositoryUsecase @Inject constructor(
    private val localRepo: ILocalRepository,
    private val iRepository: IRepository,
    private val networkManager: NetworkManager
) {

    suspend fun invoke(query: String,page:Int) : List<RepositoryItem>?{
        return if (networkManager.isNetworkAvailable()) {
            iRepository.getRepositoriesList(query,page)
        } else {
            localRepo.getAllRepositories().orEmpty().map{
                RepositoryItem(it.fullName, owner = Owner(avatarUrl = it.avatarUrl), name = it.fullName)
            }
        }
    }
}