package com.shakil.newzcompose.network

import com.shakil.newzcompose.domain.model.Article
import com.shakil.newzcompose.domain.model.Source
import com.squareup.moshi.Json
import java.util.*

data class HeadlinesResponse(
    @field:Json(name = "status") val status : String,
    @field:Json(name ="code") val errorCode : String? = null,
    @field:Json(name ="message") val errorMessage : String? = null,
    @field:Json(name ="totalResults") val totalResults : Int,
    @field:Json(name ="articles") val articles : List<NewsArticle>
)

data class NewsArticle(
        @field:Json(name = "source") val source: NewsSource,
        @field:Json(name = "author") val author: String? = null,
        @field:Json(name = "title") val title: String,
        @field:Json(name = "description") val description: String? = null,
        @field:Json(name = "url") val url: String? = null,
        @field:Json(name = "urlToImage") val urlToImage: String? = null,
        @field:Json(name = "publishedAt") val publishedAt: Date
)

data class NewsSource(
    @field:Json(name ="id") val id: String? = null,
    @field:Json(name ="name") val name: String? = null

)

fun NewsSource.toDomain() : Source {
    return Source(id = id, name = name)
}

fun NewsArticle.toDomain(): Article {
    return Article(
            source = source.toDomain(),
            author = author,
            title = title,
            description = description,
            url = url,
            urlToImage = urlToImage,
            publishedAt = publishedAt
    )
}