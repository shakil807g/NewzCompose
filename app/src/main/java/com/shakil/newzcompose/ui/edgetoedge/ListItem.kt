package com.shakil.newzcompose.ui.edgetoedge

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.glide.GlideImage

/**
 * Simple list item row which displays an image and text.
 */
@Composable
fun ListItem(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    Row(modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        GlideImage(
            data = imageUrl,
            modifier = Modifier.preferredSize(64.dp)
                .clip(RoundedCornerShape(4.dp))
        )

        Spacer(Modifier.preferredWidth(16.dp))

        Text(
            text = "Text",
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.weight(1f)
                .align(Alignment.CenterVertically)
        )
    }
}