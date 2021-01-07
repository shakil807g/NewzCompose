package com.shakil.newzcompose.domain.repository

import androidx.paging.PagingData
import com.shakil.newzcompose.domain.model.Article
import com.shakil.newzcompose.domain.model.HeadlinesPagingKey
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun topNewsHeadlines(headlinesPagingKey: HeadlinesPagingKey): Flow<PagingData<Article>>
}