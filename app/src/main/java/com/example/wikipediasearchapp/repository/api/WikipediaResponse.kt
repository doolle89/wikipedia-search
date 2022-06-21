package com.example.wikipediasearchapp.repository.api
import com.google.gson.annotations.SerializedName


data class WikipediaResponse(
    val parse: Parse? = null
)

data class Parse(
    val title: String? = null,
    @SerializedName("pageid") val pageId: Long = 0,
    val text: Text? = null
)

data class Text(
    @SerializedName("*") val x: String? = null
)