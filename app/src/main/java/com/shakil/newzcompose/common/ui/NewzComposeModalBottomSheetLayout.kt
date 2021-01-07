package com.shakil.newzcompose.common.ui

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import com.shakil.newzcompose.util.backHandler

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewzComposeModalBottomSheetLayout(
        sheetContent: @Composable (sheetState: ModalBottomSheetState) -> Unit,
        content: @Composable (sheetState: ModalBottomSheetState) -> Unit
) {

    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    backHandler(
            enabled = sheetState.value != ModalBottomSheetValue.Hidden,
            onBack = { sheetState.hide() }
    )

    NewzComposeModalBottomSheet(sheetState = sheetState,
            sheetContent = { sheetContent(sheetState) },
            content = { content(sheetState) })
}