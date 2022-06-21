package com.example.wikipediasearchapp.repository.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WikipediaSearchInterface {

    @GET("w/api.php")
    suspend fun getPage(
        @Query("page", encoded = true) topic: String,
        @Query("action", encoded = true) action: String = "parse",
        @Query("section", encoded = true) section: Int = 0,
        @Query("prop", encoded = true) prop: String = "text",
        @Query("format", encoded = true) format: String = "json"
    ): WikipediaResponse

    companion object {
        fun create(): WikipediaSearchInterface {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://en.wikipedia.org/")
                .build()
                .create(WikipediaSearchInterface::class.java)
        }
    }
}