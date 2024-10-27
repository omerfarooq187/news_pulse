package com.example.newspulse.domain.repository

import com.example.newspulse.domain.model.News

interface NewsRepository {
    suspend fun getTrendingNews(): Result<News>
    suspend fun trendingNewsNextPage(page: String): Result<News>
    suspend fun getTopHeadlines(): Result<News>
    suspend fun topHeadlinesNextPage(page: String): Result<News>
    suspend fun getPoliticsNews(): Result<News>
    suspend fun loadMorePoliticsNews(page: String): Result<News>
    suspend fun getEntertainmentNews(): Result<News>
    suspend fun loadMoreEntertainmentNews(page: String): Result<News>
    suspend fun getSportsNews(): Result<News>
    suspend fun loadMoreSportsNews(page: String): Result<News>
}