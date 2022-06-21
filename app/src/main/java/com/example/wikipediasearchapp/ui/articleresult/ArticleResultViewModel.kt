package com.example.wikipediasearchapp.ui.articleresult

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wikipediasearchapp.repository.Repository
import com.example.wikipediasearchapp.util.mapState

class ArticleResultViewModel : ViewModel() {

    private val repository = Repository.instance

    val uiStateFlow = repository.lastWikipediaArticleStateFlow.mapState(viewModelScope) {
        ArticleResultScreenUiState(articleResultUiState = it?.toArticleResultUiState())
    }

}