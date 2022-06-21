package com.example.wikipediasearchapp.repository.model

data class WikipediaArticle(
    val pageId: Long,
    val title: String,
    val body: String
)