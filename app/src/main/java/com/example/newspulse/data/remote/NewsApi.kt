package com.example.newspulse.data.remote

import com.example.newspulse.data.model.NewsResponse
import com.example.newspulse.utils.PAKISTAN_ENTERTAINMENT
import com.example.newspulse.utils.PAKISTAN_HEADLINES
import com.example.newspulse.utils.PAKISTAN_POLITICS
import com.example.newspulse.utils.PAKISTAN_SPORTS
import com.example.newspulse.utils.TRENDING_NEWS_URL
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET(TRENDING_NEWS_URL)
    suspend fun getTrendingNews(
        @Query("page") page:String? = null
    ): Response<NewsResponse>

    @GET(PAKISTAN_HEADLINES)
    suspend fun getTopHeadlines(
        @Query("page") page:String? = null
    ): Response<NewsResponse>

    @GET(PAKISTAN_POLITICS)
    suspend fun getPoliticsNews(
        @Query("page") page:String? = null
    ): Response<NewsResponse>

    @GET(PAKISTAN_ENTERTAINMENT)
    suspend fun getEntertainmentNews(
        @Query("page") page:String? = null
    ): Response<NewsResponse>

    @GET(PAKISTAN_SPORTS)
    suspend fun getSportsNews(
        @Query("page") page:String? = null
    ): Response<NewsResponse>

}