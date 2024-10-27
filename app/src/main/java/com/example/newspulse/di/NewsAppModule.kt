package com.example.newspulse.di

import com.example.newspulse.data.remote.NewsApi
import com.example.newspulse.data.repository.NewsRepositoryImpl
import com.example.newspulse.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsAppModule {

    @Provides
    @Singleton
    fun providesRetrofit() :Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://newsdata.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesNewsApi(retrofit: Retrofit): NewsApi {
        return retrofit.create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun providesNewsRepository(newsApi: NewsApi) : NewsRepository {
        return NewsRepositoryImpl(newsApi)
    }

}