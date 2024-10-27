package com.example.newspulse.data.repository

import com.example.newspulse.data.model.NewsResponse
import com.example.newspulse.data.remote.NewsApi
import com.example.newspulse.domain.model.Article
import com.example.newspulse.domain.model.News
import com.example.newspulse.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val newsApi: NewsApi) : NewsRepository {
    override suspend fun getTrendingNews(): Result<News> {
        return try {
            val response = newsApi.getTrendingNews()
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it.toDomainModel())
                } ?: Result.failure(Exception("Empty body response"))
            } else {
                Result.failure(Exception("Failed to fetch trending news"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun trendingNewsNextPage(page: String): Result<News> {
        return try {
            val response = newsApi.getTrendingNews(page)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it.toDomainModel())
                } ?: Result.failure(Exception("No more trending news"))
            } else {
                Result.failure(Exception("Failed to fetch trending news"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTopHeadlines(): Result<News> {
        return try {
            val response = newsApi.getTopHeadlines()
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it.toDomainModel())
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("Failed to fetch top headlines"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun topHeadlinesNextPage(page: String): Result<News> {
        return try {
            val response = newsApi.getTopHeadlines(page)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it.toDomainModel())
                } ?: Result.failure(Exception("No more headlines"))
            } else {
                Result.failure(Exception("Failed to fetch top headlines"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPoliticsNews(): Result<News> {
        return try {
            val response = newsApi.getPoliticsNews()
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it.toDomainModel())
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("Failed to fetch politics news"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun loadMorePoliticsNews(page: String): Result<News> {
        return try {
            val response = newsApi.getPoliticsNews(page)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it.toDomainModel())
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("Failed to fetch politics news"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getEntertainmentNews(): Result<News> {
        return try {
            val response = newsApi.getEntertainmentNews()
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it.toDomainModel())
                } ?: Result.failure(Exception("Empty body response"))
            } else {
                Result.failure(Exception("Failed to fetch entertainment news"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun loadMoreEntertainmentNews(page: String): Result<News> {
        return try {
            val response = newsApi.getEntertainmentNews(page)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it.toDomainModel())
                } ?: Result.failure(Exception("Empty body response"))
            } else {
                Result.failure(Exception("Failed to fetch entertainment news"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getSportsNews(): Result<News> {
        return try {
            val response = newsApi.getSportsNews()
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it.toDomainModel())
                } ?: Result.failure(Exception("Empty body response"))
            } else {
                Result.failure(Exception("Failed to fetch sports news"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun loadMoreSportsNews(page: String): Result<News> {
        return try {
            val response = newsApi.getSportsNews(page)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it.toDomainModel())
                } ?: Result.failure(Exception("Empty body response"))
            } else {
                Result.failure(Exception("Failed to fetch sports news"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    // Extension function to map API response to domain model
    private fun NewsResponse.toDomainModel(): News {
        return News(
            nextPage = this.nextPage,
            status = this.status,
            totalResults = this.totalResults,
            results = this.results?.map {
                Article(
                    article_id = it.article_id,
                    content = it.content,
                    country = it.country,
                    description = it.description,
                    image_url = it.image_url,
                    link = it.link,
                    pubDate = it.pubDate,
                    source_icon = it.source_icon,
                    source_name = it.source_name,
                    source_url = it.source_url,
                    title = it.title
                )
            } ?: emptyList() // Handle null articles list
        )
    }
}