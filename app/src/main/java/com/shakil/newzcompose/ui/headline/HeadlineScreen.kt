package com.shakil.newzcompose.ui.headline

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.onCommit
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.shakil.newzcompose.common.ui.JumpToTopButton
import com.shakil.newzcompose.domain.model.Article
import com.shakil.newzcompose.ui.headline.bottomsheet.HeadlineBottomSheetLayout
import com.shakil.newzcompose.ui.headline.item.ArticleItem
import com.shakil.newzcompose.ui.main.MainViewModel
import dev.chrisbanes.accompanist.insets.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayout::class)
@ExperimentalMaterialApi
@Composable
fun HeadlineScreen(headlinesViewModel: MainViewModel) {
    val context = AmbientContext.current
    val lazyPagingItems: LazyPagingItems<Article> =
        headlinesViewModel.topHeadlines.collectAsLazyPagingItems()

    val feedListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    HeadlineBottomSheetLayout(headlinesViewModel) { headlineBottomSheetController ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                state = feedListState,
                contentPadding = AmbientWindowInsets.current.systemBars
                    .toPaddingValues()
                    .add(start = 8.dp,top = 16.dp,bottom = 68.dp,end = 8.dp)
            ) {
                //item { headerItem() }

                when (val refreshState = lazyPagingItems.loadState.refresh) {
                    is LoadState.Loading -> {
                        item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
                    }
                    is LoadState.Error -> {
                        item {
                            ErrorItem(
                                refreshState.error.localizedMessage!!,
                                modifier = Modifier.fillParentMaxSize()
                            ) {
                                lazyPagingItems.retry()
                            }
                        }
                    }
                    is LoadState.NotLoading -> {
                        itemsIndexed(lazyPagingItems) { index, item ->
                            ArticleItem(item!!, headlinesViewModel) {

                            }
                        }
                    }
                }
                when (val appendState = lazyPagingItems.loadState.append) {
                    is LoadState.Loading -> {
                        item { LoadingItem() }
                    }
                    is LoadState.Error -> {
                        item {
                            ErrorItem(appendState.error.localizedMessage!!) {
                                lazyPagingItems.retry()
                            }
                        }
                    }
                }
            }

            //Threshold for button showing
            val jumpToTopButtonEnabled = feedListState.firstVisibleItemIndex > 2

            JumpToTopButton(
                // Only show if the scroller is not at the top
                enabled = jumpToTopButtonEnabled,
                onClicked = {
                    coroutineScope.launch {
                        feedListState.snapToItemIndex(0)
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
            )

            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .navigationBarsPadding()
                    .padding(bottom = 16.dp, end = 16.dp)
                    .preferredSize(48.dp),
                onClick = {
                    headlineBottomSheetController.countrySheetState.show()
                }
            ) {
                Icon(imageVector = Icons.Outlined.Settings)
            }
        }

    }
}

@Composable
fun LoadingView(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun LoadingItem() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

@Composable
fun ErrorItem(
    message: String,
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
) {
    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = message,
            maxLines = 1,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.h6,
            color = Color.Red
        )
        OutlinedButton(onClick = onClickRetry) {
            Text(text = "Try again")
        }
    }
}

@Composable
fun NewsFeedItem(index: Int, item: Article?, onItemSelected: (Article) -> Unit) {
    if (item != null) {
        val cardModifier = Modifier.run {
            if (index == 0) {
                padding(20.dp)
            } else {
                padding(
                    start = 20.dp,
                    top = 0.dp,
                    end = 20.dp,
                    bottom = 20.dp
                )
            }
        }

        ArticleItem(
            modifier = cardModifier,
            article = item,
            onClick = { article -> onItemSelected(article) })
    }
}
