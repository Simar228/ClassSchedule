package com.example.classschedule.Presentation.utilScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CloudOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.classschedule.Data.util.NetworkMonitor
import com.example.classschedule.Presentation.ui.utils.StyleButton


@Composable
fun NoInternetScreen(
    navigateTo: () -> Unit
) {
    val isOnline by NetworkMonitor.isAvailable.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            imageVector = Icons.Rounded.CloudOff,
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            tint = Color.Black
        )

        Spacer(modifier = Modifier.height(32.dp))


        Text(
            text = "Whoops!",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))


        Text(
            text = "It seems you've wandered off the grid. Please check your connection and try again.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(48.dp))


        StyleButton(
            onClick = {
                navigateTo()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),

        ) {
            Text(
                text = "Try Again",
                style = MaterialTheme.typography.titleMedium
            )
        }

    }
}




@Preview(showBackground = true)
@Composable
private fun ShowNoInternetScreen(){
    NoInternetScreen {}

}