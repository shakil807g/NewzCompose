package com.shakil.newzcompose.domain.repository

import androidx.paging.PagingSource
import com.shakil.newzcompose.domain.model.Article
import com.shakil.newzcompose.domain.model.HeadlinesPagingKey
import com.shakil.newzcompose.domain.model.toQueryParamValue
import com.shakil.newzcompose.network.NewsApiService
import com.shakil.newzcompose.network.toDomain

class PageKeyedHeadlinesPagingSource(
        private val initialKey: HeadlinesPagingKey,
        private val newsApiService: NewsApiService
) : PagingSource<HeadlinesPagingKey, Article>() {
    override suspend fun load(params: LoadParams<HeadlinesPagingKey>): LoadResult<HeadlinesPagingKey, Article> {
        return try {
            val requestParam = params.key ?: initialKey

            val data = newsApiService.getTopHeadlines(
                    country = requestParam.country.countryCode,
                    category = requestParam.category.toQueryParamValue(),
                    pageSize = requestParam.pageSize,
                    page = requestParam.page
            )

            LoadResult.Page(
                    data = data.articles.map { it.toDomain() },
                    prevKey = null,
                    nextKey = createNextKey(requestParam, data.totalResults),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private fun createNextKey(
            currentKey: HeadlinesPagingKey,
            totalResults: Int
    ): HeadlinesPagingKey? {
        val currentMaxResults = currentKey.pageSize * currentKey.page

        return if (currentMaxResults < totalResults) {
            currentKey.copy(page = currentKey.page + 1)
        } else {
            null
        }
    }
}