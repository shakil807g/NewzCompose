package com.shakil.newzcompose.domain.model

data class HeadlinesPagingKey(
        val country: Country = Country.USA,
        val category: NewsCategory = NewsCategory.ALL,
        val pageSize: Int = 20,
        val page: Int = 1
)