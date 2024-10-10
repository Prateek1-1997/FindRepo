package com.example.teachmintapplication.data

import android.content.Context
import com.abhisek.project.bookshelf.data.local.dao.LocalRepoDao
import com.abhisek.project.bookshelf.data.local.database.RepositoryDatabase
import com.abhisek.project.bookshelf.data.local.repository.LocalRepoImpl
import com.example.teachmintapplication.domain.ILocalRepo
import com.example.teachmintapplication.domain.IRepository
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
    fun provideLocalRepo(
        repositoryDao: LocalRepoDao
    ): ILocalRepo {
        return LocalRepoImpl (repositoryDao)
    }

    @Provides
    @Singleton
    fun provideDao(repositoryDatabase : RepositoryDatabase): LocalRepoDao {
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
    fun provideService(retrofit: Retrofit): GithubService{
        return retrofit.create(GithubService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(repoImpl: RepoImpl) : IRepository{
        return repoImpl
    }

}