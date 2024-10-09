package com.example.teachmintapplication.data

import com.example.teachmintapplication.domain.IRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProviderModule {


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