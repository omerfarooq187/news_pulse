package com.example.newspulse.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newspulse.domain.model.News
import com.example.newspulse.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {
    private val _trendingNewsState = MutableStateFlow<UiState<News>>(UiState.Loading)
    val trendingNewsState: StateFlow<UiState<News>> = _trendingNewsState
    private val _topHeadlinesState = MutableStateFlow<UiState<News>>(UiState.Loading)
    val topHeadlinesState: StateFlow<UiState<News>> = _topHeadlinesState
    private val _politicsNewsState = MutableStateFlow<UiState<News>>(UiState.Loading)
    val politicsNewsState: StateFlow<UiState<News>> = _politicsNewsState
    private val _entertainmentNewsState = MutableStateFlow<UiState<News>>(UiState.Loading)
    val entertainmentNewsState: StateFlow<UiState<News>> = _entertainmentNewsState
    private val _sportsNewsState = MutableStateFlow<UiState<News>>(UiState.Loading)
    val sportsNewsState: StateFlow<UiState<News>> = _sportsNewsState

    init {
        viewModelScope.launch {
            newsRepository.getTrendingNews()
                .onSuccess {
                    _trendingNewsState.value = UiState.Success(it)
                }
                .onFailure {
                    _trendingNewsState.value = UiState.Error(it.message!!)
                }
        }
        viewModelScope.launch {
            newsRepository.getTopHeadlines()
                .onSuccess {
                    _topHeadlinesState.value = UiState.Success(it)
                }
                .onFailure {
                    _topHeadlinesState.value = UiState.Error(it.message!!)
                }
        }
    }

    fun loadMoreTrendingNews(page: String) {
        viewModelScope.launch {
            newsRepository.trendingNewsNextPage(page)
                .onSuccess { news ->
                    val currentNews = (_trendingNewsState.value as? UiState.Success)?.data
                    val updatedNews =
                        currentNews?.copy(results = currentNews.results + news.results) ?: news
                    _trendingNewsState.value = UiState.Success(updatedNews)
                }
                .onFailure {
                    _trendingNewsState.value = UiState.Error(it.message!!)
                }
        }
    }

    fun loadMoreHeadlines(page: String) {
        viewModelScope.launch {
            newsRepository.topHeadlinesNextPage(page)
                .onSuccess { news ->
                    val currentNews = (_topHeadlinesState.value as? UiState.Success)?.data
                    val updatedNews =
                        currentNews?.copy(results = currentNews.results + news.results) ?: news
                    _topHeadlinesState.value = UiState.Success(updatedNews)
                }
                .onFailure {
                    _topHeadlinesState.value = UiState.Error(it.message!!)
                }
        }
    }

    fun getPoliticsNews() {
        viewModelScope.launch {
            newsRepository.getPoliticsNews()
                .onSuccess { politicsNews ->
                    _politicsNewsState.value = UiState.Success(politicsNews)
                }
                .onFailure {
                    _politicsNewsState.value = UiState.Error(it.message!!)
                }
        }
    }

    fun loadMorePoliticsNews(page: String) {
        viewModelScope.launch {
            newsRepository.loadMorePoliticsNews(page)
                .onSuccess { news ->
                    val currentNews = (_politicsNewsState.value as? UiState.Success)?.data
                    val updatedNews =
                        currentNews?.copy(results = currentNews.results + news.results) ?: news
                    _politicsNewsState.value = UiState.Success(updatedNews)
                }
        }
    }

    fun getEntertainmentNews() {
        viewModelScope.launch {
            newsRepository.getEntertainmentNews()
                .onSuccess { entertainmentNews ->
                    _entertainmentNewsState.value = UiState.Success(entertainmentNews)
                }
                .onFailure {
                    _entertainmentNewsState.value = UiState.Error(it.message!!)
                }
        }
    }

    fun loadMoreEntertainmentNews(page: String) {
        viewModelScope.launch {
            newsRepository.loadMoreEntertainmentNews(page)
                .onSuccess { news ->
                    val currentNews = (_entertainmentNewsState.value as? UiState.Success)?.data
                    val updatedNews = currentNews?.copy(results = currentNews.results + news.results) ?: news
                    _entertainmentNewsState.value = UiState.Success(updatedNews)
                }
                .onFailure {
                    _entertainmentNewsState.value = UiState.Error(it.message!!)
                }
        }
    }

    fun getSportsNews() {
        viewModelScope.launch {
            newsRepository.getSportsNews()
                .onSuccess { sportsNews ->
                    _sportsNewsState.value = UiState.Success(sportsNews)
                }
                .onFailure {
                    _sportsNewsState.value = UiState.Error(it.message!!)
                }
        }
    }

    fun loadMoreSportsNews(page: String) {
        viewModelScope.launch {
            newsRepository.loadMoreSportsNews(page)
                .onSuccess { news->
                    val currentNews = (_sportsNewsState.value as? UiState.Success)?.data
                    val updatedNews = currentNews?.copy(results = currentNews.results + news.results)?:news
                    _sportsNewsState.value = UiState.Success(updatedNews)
                }
                .onFailure {
                    _sportsNewsState.value = UiState.Error(it.message!!)
                }
        }
    }
}

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}