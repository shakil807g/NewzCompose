package com.shakil.newzcompose.ui.main

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shakil.newzcompose.domain.model.*
import com.shakil.newzcompose.domain.repository.NewsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import java.util.*


private const val TAG = "MainViewModel"
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
             newsSelection.flatMapLatest {
                 newsRepository.topNewsHeadlines(it)
             }.cachedIn(viewModelScope)


    fun setNewsCategory(newsCategory: NewsCategory) {
        newsSelection.value = newsSelection.value.copy(category = newsCategory)
    }

    fun setCountry(country : Country) {
        newsSelection.value = newsSelection.value.copy(country = country)
    }


    fun someFakeArticles() = flow {
        val list = listOf<Article>(
            Article(Source("1","asdasd"),"adsadas","dsdsdsdsd","asdadasd",
                "sdsdsdsd", "",Date()),
            Article(Source("1","asdasd"),"adsadas","dsdsdsdsd","asdadasd",
                "sdsdsdsd", "",Date()))
        delay(4000)
        emit(list)
    }
}