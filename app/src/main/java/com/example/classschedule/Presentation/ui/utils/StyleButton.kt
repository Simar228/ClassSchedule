package com.example.classschedule.Presentation.ui.utils

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.unit.dp


@Composable
fun StyleButton(
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content:  @Composable () -> Unit
) {
    Button(
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            disabledContentColor = Color.White,
//            containerColor = Color(0xFF1C1C1E),
//            contentColor = Color.White,
            disabledContainerColor = Color(0xFFE5E5EA),
        ),

    ) {
        content()
    }
}