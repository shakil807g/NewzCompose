package com.shakil.newzcompose.ui.headline.bottomsheet

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.shakil.newzcompose.R
import com.shakil.newzcompose.common.ui.ChoiceChipContent
import com.shakil.newzcompose.domain.model.NewsCategory
import com.shakil.newzcompose.ui.main.MainViewModel
import dev.chrisbanes.accompanist.insets.navigationBarsPadding

@ExperimentalLayout
@Composable
fun SelectAnotherBottomSheet(
    headlinesViewModel: MainViewModel,
    onSelectCategory: (NewsCategory) -> Unit = {}
) {

    val selectedCategory = remember { mutableStateOf(
        headlinesViewModel.newsSelection.value.category
    ) }

    Column(
        modifier = Modifier
            .navigationBarsPadding()
            .padding(8.dp)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(12.dp),
            style = MaterialTheme.typography.h6,
            text = stringResource(id = R.string.select_another_category)
        )
        Divider()
        Spacer(modifier = Modifier.height(16.dp))
        FlowRow(
            mainAxisSize = SizeMode.Expand,
            mainAxisSpacing = 8.dp,
            crossAxisSpacing = 8.dp
        ) {
            NewsCategory.values().toList().forEach {
                ChoiceChipContent(it, it.name == selectedCategory.value.name) {
                    selectedCategory.value = it
                    onSelectCategory(it)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
//        LazyGridFor(
//                items = NewsCategory.values().toList(),
//                rowSize = 2
//        ) { category ->
//            Column(
//                    modifier = Modifier
//                            .fillMaxWidth()
//                            .preferredHeight(74.dp)
//                            .clickable(onClick = { onSelectCategory(category) })
//                            .padding(8.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text(text = category.emoji())
//                Text(text = stringResource(id = category.res))
//            }
//        }
    }
}