package com.busan.wallet.feature.miniapp.common.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.busan.wallet.core.ui.language.LocalStrings

@Composable
fun ErrorContent(
    error: String,
    onRetry: () -> Unit
) {
    val strings = LocalStrings.current
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = error,
            style = MaterialTheme.typography.bodyLarge
        )
        TextButton(onClick = onRetry) {
            Text(strings.tryAgain)
        }
    }
}