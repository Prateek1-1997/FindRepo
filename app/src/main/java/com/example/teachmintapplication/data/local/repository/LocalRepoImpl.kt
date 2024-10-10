package com.abhisek.project.bookshelf.data.local.repository

import com.abhisek.project.bookshelf.data.local.dao.LocalRepoDao
import com.example.teachmintapplication.data.local.entity.ItemEntity
import com.example.teachmintapplication.domain.local.ILocalRepo
import javax.inject.Inject
class LocalRepoImpl @Inject constructor(
    private val repositoriesDao: LocalRepoDao,
) : ILocalRepo {
    override suspend fun getAllRepositories(): List<ItemEntity>? {
        return repositoriesDao.getAllRepositories()
    }


}