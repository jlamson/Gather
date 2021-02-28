package com.darkmoose117.gather.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.darkmoose117.gather.R
import com.darkmoose117.gather.util.ThemedPreview

@Composable
fun ErrorCard(@StringRes errorMessage: Int = R.string.generic_error) = ErrorCard(
    stringResource(id = errorMessage)
)

@Composable
fun ErrorCard(errorMessage: String) {
    Box(Modifier.padding(vertical = 32.dp, horizontal = 16.dp)) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Outlined.Error, contentDescription = null, tint = MaterialTheme.colors.error)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = errorMessage, style = MaterialTheme.typography.subtitle1)
            }
        }
    }
}


@Preview
@Composable
fun LightError() {
    ThemedPreview {
        ErrorCard()
    }
}

@Preview
@Composable
fun DarkSError() {
    ThemedPreview(darkTheme = true) {
        ErrorCard()
    }
}