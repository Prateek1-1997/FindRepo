package com.example.teachmintapplication.data.di
import android.content.Context
import com.example.teachmintapplication.data.local.dao.LocalRepositoryDao
import com.example.teachmintapplication.data.local.database.RepositoryDatabase
import com.example.teachmintapplication.data.local.repository.LocalRepositoryImpl
import com.example.teachmintapplication.data.remote.service.GithubService
import com.example.teachmintapplication.data.remote.NetworkManager
import com.example.teachmintapplication.data.repository.RepositoryImpl
import com.example.teachmintapplication.domain.local.ILocalRepository
import com.example.teachmintapplication.domain.remote.IRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProviderModule {


    @Provides
    @Singleton
    fun provideLocalRepository(
        repositoryDao: LocalRepositoryDao
    ): ILocalRepository {
        return LocalRepositoryImpl (repositoryDao)
    }

    @Provides
    @Singleton
    fun provideDao(repositoryDatabase : RepositoryDatabase): LocalRepositoryDao {
        return repositoryDatabase.repositoryDao
    }

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context): RepositoryDatabase {
        return RepositoryDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideNetworkManager(@ApplicationContext context: Context): NetworkManager {
        return NetworkManager(context)
    }


    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): GithubService {
        return retrofit.create(GithubService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(repoImpl: RepositoryImpl) : IRepository {
        return repoImpl
    }

}