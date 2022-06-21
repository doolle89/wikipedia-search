package com.example.wikipediasearchapp.repository

import com.example.wikipediasearchapp.repository.api.WikipediaSearchService
import com.example.wikipediasearchapp.repository.model.WikipediaArticle
import com.example.wikipediasearchapp.repository.model.toWikipediaArticle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class Repository private constructor(
    private val wikipediaSearchService: WikipediaSearchService
) {

    private val _lastWikipediaArticleStateFlow: MutableStateFlow<WikipediaArticle?> = MutableStateFlow(null)
    val lastWikipediaArticleStateFlow: StateFlow<WikipediaArticle?> = _lastWikipediaArticleStateFlow

    suspend fun getWikipediaArticle(topic: String): WikipediaArticle {
        val result = wikipediaSearchService.getPage(topic).toWikipediaArticle()
        _lastWikipediaArticleStateFlow.value = result
        return result
    }

    companion object {
        val instance by lazy { Repository(WikipediaSearchService()) }
    }
}