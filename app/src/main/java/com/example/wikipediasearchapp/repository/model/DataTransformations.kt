package com.example.wikipediasearchapp.repository.model

import com.example.wikipediasearchapp.repository.api.WikipediaResponse

fun WikipediaResponse.toWikipediaArticle(): WikipediaArticle {
    return WikipediaArticle(
        pageId = parse?.pageId ?: throw IllegalArgumentException("Article pageId is missing"),
        title = parse.title ?: throw IllegalArgumentException("Article title is missing"),
        body = parse.text?.x ?: throw IllegalArgumentException("Article body is missing")
    )
}