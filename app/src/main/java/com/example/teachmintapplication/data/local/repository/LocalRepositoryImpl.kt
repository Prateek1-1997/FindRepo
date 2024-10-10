package com.example.teachmintapplication.data.local.repository
import com.example.teachmintapplication.data.local.dao.LocalRepositoryDao
import com.example.teachmintapplication.data.local.entity.ItemEntity
import com.example.teachmintapplication.domain.local.ILocalRepository
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val repositoriesDao: LocalRepositoryDao
) : ILocalRepository {

    override suspend fun getAllRepositories(): List<ItemEntity>? {
        return repositoriesDao.getAllRepositories()
    }


}