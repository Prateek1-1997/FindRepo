package com.example.teachmintapplication.data.local.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.teachmintapplication.data.local.entity.ItemEntity

@Dao
interface LocalRepositoryDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepositories(repositories: List<ItemEntity>)

    @androidx.room.Query("SELECT * FROM repositories ORDER BY ID DESC LIMIT 15")
    suspend fun getAllRepositories(): List<ItemEntity>

}