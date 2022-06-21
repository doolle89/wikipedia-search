package com.example.wikipediasearchapp.ui.articlesearch

import android.content.Context
import androidx.annotation.StringRes

data class ArticleSearchScreenUiState(
    val isLoading: Boolean = false,
    val isResultReady: Boolean = false,
    val errorMessages: List<StringResource> = emptyList()
)

class StringResource(
    @StringRes private val id: Int,
    private vararg val formatArgs: Any? = emptyArray()
) {
    fun resolve(context: Context): String {
        return context.getString(id, *formatArgs)
    }

    override fun equals(other: Any?): Boolean {
        if (other !is StringResource) return false
        if (id != other.id) return false
        if (!formatArgs.contentEquals(other.formatArgs)) return false
        return true
    }

    override fun hashCode(): Int {
        return 31 * id + formatArgs.contentHashCode()
    }
}