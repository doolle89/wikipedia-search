package com.example.wikipediasearchapp.repository.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class WikipediaSearchService(
    private val coroutineContext: CoroutineContext = Dispatchers.IO
){
    private val wikipediaApi = WikipediaSearchInterface.create()

    suspend fun getPage(topic: String): WikipediaResponse = withContext(coroutineContext) {
        wikipediaApi.getPage(topic)
    }
}