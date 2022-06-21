package com.example.wikipediasearchapp.ui.articleresult

import com.example.wikipediasearchapp.repository.model.WikipediaArticle

data class ArticleResultScreenUiState(
    val articleResultUiState: ArticleResultUiState? = null
)

data class ArticleResultUiState(
    val title: String,
    val body: String
)

fun WikipediaArticle.toArticleResultUiState(): ArticleResultUiState {
    return ArticleResultUiState(
        title = title,
        body = body
    )
}