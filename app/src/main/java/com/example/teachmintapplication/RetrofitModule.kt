package com.example.teachmintapplication

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {


     @Provides
     @Singleton
     fun provideHttpLoggingInterceptor()  :HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
         level = HttpLoggingInterceptor.Level.BODY
     }

    @Provides
    @Singleton
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) : OkHttpClient = OkHttpClient
        .Builder()
        .addNetworkInterceptor(httpLoggingInterceptor)
        .build()




      @Provides
      @Singleton
      fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit = Retrofit.Builder()
          .client(okHttpClient)
          .addConverterFactory(GsonConverterFactory.create())
          .baseUrl("https://api.github.com")
          .build()


}