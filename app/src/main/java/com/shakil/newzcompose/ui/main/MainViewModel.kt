package com.shakil.newzcompose.ui.main

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.shakil.newzcompose.domain.model.Article
import com.shakil.newzcompose.domain.model.Country
import com.shakil.newzcompose.domain.model.HeadlinesPagingKey
import com.shakil.newzcompose.domain.model.NewsCategory
import com.shakil.newzcompose.domain.repository.NewsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
        private val newsRepository: NewsRepository,
        @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    init {
        Log.d("MainViewModel", ": ")
    }

    val newsSelection : MutableStateFlow<HeadlinesPagingKey> = MutableStateFlow(
            HeadlinesPagingKey())

    val selectedNewsCategory : MutableStateFlow<NewsCategory> = MutableStateFlow(NewsCategory.ALL)

     @ExperimentalCoroutinesApi
     val topHeadlines: Flow<PagingData<Article>> =
             newsSelection.flatMapLatest { newsRepository.topNewsHeadlines(it) }


    fun setNewsCategory(newsCategory: NewsCategory) {
        newsSelection.value = newsSelection.value.copy(category = newsCategory)
    }

    fun setCountry(country : Country) {
        newsSelection.value = newsSelection.value.copy(country = country)
    }
}