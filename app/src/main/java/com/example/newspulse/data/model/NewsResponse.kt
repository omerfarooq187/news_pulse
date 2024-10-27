package com.example.newspulse.data.model

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val results: List<ArticleResponse>?,
    val nextPage: String
)

data class ArticleResponse(
    val article_id: String,
    val content: String,
    val country: List<String>,
    val description: String,
    val image_url: String,
    val link: String,
    val pubDate: String,
    val source_icon: String,
    val source_name: String,
    val source_url: String,
    val title: String
)
