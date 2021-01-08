package com.shakil.newzcompose.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.insets.statusBarsPadding

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewzComposeModalBottomSheet(
        sheetState: ModalBottomSheetState,
        sheetContent: @Composable () -> Unit,
        content: @Composable () -> Unit
) = ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetBackgroundColor = Color.Transparent,
        sheetContentColor = contentColorFor(color = MaterialTheme.colors.surface),
        sheetElevation = 0.dp,
        sheetContent = {
            Box(modifier = Modifier
                    .statusBarsPadding()
                    .clip(RoundedCornerShape(16.dp))) {
                Surface(
                        elevation = ModalBottomSheetDefaults.Elevation,
                ) {
                    sheetContent()
                }
            }
        },
        content = content
)