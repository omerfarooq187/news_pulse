package com.example.newspulse.domain.model

data class News(
    val nextPage: String?,
    val results: List<Article>,
    val status: String,
    val totalResults: Int
)