package com.mitteloupe.whoami.home.ui.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mitteloupe.whoami.home.ui.model.IconLabelUiModel

@Composable
fun DetailsRow(
    detailsItem: IconLabelUiModel,
    labelResourceId: Int,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = detailsItem.iconResourceId),
            contentDescription = null,
            modifier = Modifier
                .width(36.dp)
                .height(32.dp)
                .padding(0.dp, 0.dp, 8.dp, 0.dp),
            colorFilter = ColorFilter.tint(
                color = MaterialTheme.colorScheme.onSurface,
                blendMode = BlendMode.Modulate
            ),
            contentScale = ContentScale.Inside
        )
        Text(
            text = stringResource(labelResourceId),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .width(108.dp)
        )
        Text(
            text = detailsItem.label,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
