package com.shakil.newzcompose.common.ui

import androidx.compose.animation.DpPropKey
import androidx.compose.animation.core.transitionDefinition
import androidx.compose.animation.transition
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val bottomOffset = DpPropKey("Bottom Offset")

private val definition = transitionDefinition<Visibility> {
    state(Visibility.GONE) {
        this[bottomOffset] = (-32).dp
    }
    state(Visibility.VISIBLE) {
        this[bottomOffset] = 32.dp
    }
}

private enum class Visibility {
    VISIBLE,
    GONE
}

/**
 * Shows a button that lets the user scroll to the top.
 */
@Composable
fun JumpToTopButton(
        enabled: Boolean,
        onClicked: () -> Unit,
        modifier: Modifier = Modifier
) {
    // Show Jump to Bottom button
    val transition = transition(
            definition = definition,
            toState = if (enabled) Visibility.VISIBLE else Visibility.GONE
    )
    if (transition[bottomOffset] > 0.dp) {
        ExtendedFloatingActionButton(
                icon = {
                    Icon(
                            tint = Color.White,
                            imageVector = Icons.Filled.ArrowUpward,
                            modifier = Modifier.preferredHeight(18.dp)
                    )
                },
                text = {
                    Text(text = "Jump to Top",style = TextStyle(color = Color.White))
                },
                onClick = onClicked,
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.primary,
                modifier = modifier
                        .offset(x = 0.dp, y = -transition[bottomOffset])
                        .preferredHeight(36.dp)
        )
    }
}

@Preview
@Composable
fun JumpToTopPreview() {
    JumpToTopButton(enabled = true, onClicked = {})
}