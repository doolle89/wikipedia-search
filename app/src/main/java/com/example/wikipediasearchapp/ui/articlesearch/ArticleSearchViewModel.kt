package com.example.wikipediasearchapp.ui.articlesearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wikipediasearchapp.R
import com.example.wikipediasearchapp.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArticleSearchViewModel : ViewModel() {

    private val repository = Repository.instance

    private val _uiStateFlow = MutableStateFlow(ArticleSearchScreenUiState())
    val uiStateFlow: StateFlow<ArticleSearchScreenUiState> = _uiStateFlow

    fun articleSearch(topic: String) {
        viewModelScope.launch {
            setLoadingState(true)
            try {
                repository.getWikipediaArticle(topic)
                _uiStateFlow.value = _uiStateFlow.value.copy(isResultReady = true)
            } catch (e: Exception) {
                setErrorMessage(StringResource(R.string.error_article_result, e.localizedMessage))
            } finally {
                setLoadingState(false)
            }
        }
    }

    fun setResultHandled() {
        _uiStateFlow.value.let {
            _uiStateFlow.value = it.copy(isResultReady = false)
        }
    }

    fun setErrorMessageShown(errorMessage: StringResource) {
        _uiStateFlow.value.let {
            _uiStateFlow.value = it.copy(errorMessages = it.errorMessages.filterNot { it == errorMessage })
        }
    }

    private fun setErrorMessage(errorMessage: StringResource) {
        _uiStateFlow.value.let {
            _uiStateFlow.value = it.copy(errorMessages = it.errorMessages + errorMessage)
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        _uiStateFlow.value = _uiStateFlow.value.copy(isLoading = isLoading)
    }
}