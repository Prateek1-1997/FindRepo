package com.example.teachmintapplication.domain.local

import com.example.teachmintapplication.data.local.entity.ItemEntity


interface ILocalRepo {


    suspend fun getAllRepositories() : List<ItemEntity>?



}