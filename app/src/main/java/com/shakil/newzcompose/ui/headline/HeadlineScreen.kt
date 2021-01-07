package com.shakil.newzcompose.ui.headline

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import dev.chrisbanes.accompanist.insets.AmbientWindowInsets
import dev.chrisbanes.accompanist.insets.add
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.toPaddingValues
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun HeadlineScreen(headlinesViewModel: MainViewModel) {

    val context = AmbientContext.current
    val lazyPagingItems: LazyPagingItems<Article> =
            headlinesViewModel.topHeadlines.collectAsLazyPagingItems()

    val feedListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Box {

        HeadlineBottomSheetLayout { headlineBottomSheetController ->
            LazyColumn(
                    state = feedListState,
                    contentPadding = AmbientWindowInsets.current.systemBars
                            .toPaddingValues()
                            .add(bottom = 68.dp)
            ) {
                //item { headerItem() }

                when (val refreshState = lazyPagingItems.loadState.refresh) {
                    is LoadState.Loading -> {
                        //item { NewsFeedLoading() }
                    }
                    is LoadState.Error -> {
                        item {
//                        NewsFeedError(error = refreshState.error) {
//                            lazyPagingItems.retry()
//                        }
                        }
                    }
                    is LoadState.NotLoading -> {
                        itemsIndexed(lazyPagingItems) { index, item ->
                            ArticleItem(item!!) {

                            }
                        }
                    }
                }
                when (val appendState = lazyPagingItems.loadState.append) {
                    is LoadState.Loading -> {
                        //item { NewsFeedPageLoading() }
                    }
                    is LoadState.Error -> {
                        item {
//                        NewsFeedPageError(error = appendState.error) {
//                            lazyPagingItems.retry()
//                        }
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
