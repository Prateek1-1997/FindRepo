package com.example.teachmintapplication.data.local.database
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.teachmintapplication.data.local.dao.LocalRepositoryDao
import com.example.teachmintapplication.data.local.entity.ItemEntity
import javax.inject.Singleton

@Singleton
@Database(
    entities = [ItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class RepositoryDatabase : RoomDatabase() {

    abstract val repositoryDao: LocalRepositoryDao

    companion object {
        @Volatile
        private var INSTANCE: RepositoryDatabase? = null
        fun getInstance(context: Context): RepositoryDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    RepositoryDatabase::class.java,
                    "repository_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}