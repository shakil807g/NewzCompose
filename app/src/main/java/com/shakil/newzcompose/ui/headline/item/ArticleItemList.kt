package com.shakil.newzcompose.ui.headline.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.shakil.newzcompose.domain.model.Article
import com.shakil.newzcompose.ui.main.MainViewModel
import dev.chrisbanes.accompanist.coil.CoilImage
import java.util.*
import kotlin.math.abs

@Composable
fun ArticleItem(
        article: Article,
        headlinesViewModel: MainViewModel,
        onClick: (Article) -> Unit = {},
) {
    val context = AmbientContext.current
    val isExpanded = remember { mutableStateOf(false) }

    Surface(modifier = Modifier
            .fillMaxWidth()
            .clickable(
                    onClick = {
                        //isExpanded.value = !isExpanded.value
                        onClick(article)
                        openArticle(article, context)
                    },
                    indication = null
            ),
            //.padding(8.dp),
            color = MaterialTheme.colors.background,
            //elevation = 4.dp,
            //shape = RoundedCornerShape(8.dp)
    ) {

        Column {
            Text(
                modifier = Modifier.wrapContentHeight(),
                text = formatArticleTitle(article.title),
                style = MaterialTheme.typography.h6)
            if (article.description?.isNotEmpty() == true) {
                Text(
                    modifier = Modifier.wrapContentHeight(),
                    text = article.description,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = if(isExpanded.value) Int.MAX_VALUE else 2,
                    style = MaterialTheme.typography.body1
                )
            }

            CoilImage(
                modifier = Modifier
                    //.preferredHeight(100.dp)
                    .aspectRatio(1.77f),
                //.clip(RoundedCornerShape(4.dp)),
                data = article.urlToImage ?: "",
                contentScale = ContentScale.Crop,
                fadeIn = true,
                loading = {
                    Box(Modifier.fillMaxWidth()) {
                        Image(
                            modifier = Modifier.preferredSize(24.dp).align(Alignment.Center),
                            imageVector = Icons.Outlined.Image,
                            colorFilter = ColorFilter.tint(contentColorFor(color = androidx.compose.ui.graphics.Color.Gray))
                        )
                    }
                },
                error = {
                    Box(Modifier.fillMaxWidth()) {
                        Image(
                            modifier = Modifier.preferredSize(24.dp).align(Alignment.Center),
                            imageVector = Icons.Outlined.Error,
                            colorFilter = ColorFilter.tint(MaterialTheme.colors.error)
                        )
                    }
                }
            )

            Row(
                modifier = Modifier.wrapContentHeight()
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = article.source.name.toString(),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.caption
                )
                Text(
                    text = " - ${formatTimeSincePublished(article.publishedAt)}",
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.caption
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = {
                        //shareArticle(article, context)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Share,
                        modifier = Modifier.preferredSize(20.dp)
                    )
                }
            }
        }
    }
}

private fun formatArticleTitle(title: String): String {
    return title.substringBeforeLast(delimiter = " - ")
}

private fun formatTimeSincePublished(date: Date): String {
    val now = Calendar.getInstance().time
    val difference: Long = abs(date.time - now.time)
    val differenceInHours: Long = difference / (60 * 60 * 1000)
    return "$differenceInHours hours ago"
}