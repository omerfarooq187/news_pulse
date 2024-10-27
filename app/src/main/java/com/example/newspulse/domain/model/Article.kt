package com.example.newspulse.domain.model

data class Article(
    val article_id: String,
    val content: String,
    val country: List<String>,
    val description: String?,
    val image_url: String?,
    val link: String,
    val pubDate: String,
    val source_icon: String?,
    val source_name: String?,
    val source_url: String?,
    val title: String,
)