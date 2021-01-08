package com.shakil.newzcompose.ui.headline.bottomsheet

import androidx.compose.foundation.layout.ExperimentalLayout
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import com.shakil.newzcompose.common.ui.NewzComposeModalBottomSheet
import com.shakil.newzcompose.ui.main.MainViewModel
import com.shakil.newzcompose.util.backHandler

@ExperimentalLayout
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HeadlineBottomSheetLayout(
    headlinesViewModel: MainViewModel,
    content: @Composable (headlineBottomSheetController: HeadlinesBottomSheetController) -> Unit
) {

    val categorySheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val anotherSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    backHandler(
        enabled = categorySheetState.value != ModalBottomSheetValue.Hidden,
        onBack = { categorySheetState.hide() }
    )

    backHandler(
        enabled = categorySheetState.value != ModalBottomSheetValue.Hidden,
        onBack = { anotherSheetState.hide() }
    )

    NewzComposeModalBottomSheet(
        sheetState = categorySheetState,
        sheetContent = {
            SelectCategoryBottomSheet { newsCategory ->
                categorySheetState.hide()
            }
        },
        content = {
            NewzComposeModalBottomSheet(
                sheetState = anotherSheetState,
                sheetContent = {
                    SelectAnotherBottomSheet(headlinesViewModel) { newsCategory ->
                        headlinesViewModel.setNewsCategory(newsCategory = newsCategory)
                        anotherSheetState.hide()
                    }
                },
                content = {
                    content(HeadlinesBottomSheetController(categorySheetState, anotherSheetState))
                })
        })
}

@OptIn(ExperimentalMaterialApi::class)
class HeadlinesBottomSheetController(
    val categorySheetState: ModalBottomSheetState,
    val countrySheetState: ModalBottomSheetState
)

