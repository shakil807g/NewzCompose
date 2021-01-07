package com.shakil.newzcompose.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shakil.newzcompose.domain.model.Article
import com.shakil.newzcompose.domain.model.HeadlinesPagingKey
import com.shakil.newzcompose.network.NewsApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(
        private val newsApiService: NewsApiService
) : NewsRepository {

    override fun topNewsHeadlines(headlinesPagingKey: HeadlinesPagingKey): Flow<PagingData<Article>> {
        return Pager(
                PagingConfig(
                        pageSize = headlinesPagingKey.pageSize,
                        prefetchDistance = headlinesPagingKey.pageSize.div(4)
                )
        ) {
            PageKeyedHeadlinesPagingSource(
                    initialKey = headlinesPagingKey,
                    newsApiService = newsApiService
            )
        }.flow
    }
}