package com.example.classschedule.Presentation.main.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.classschedule.Presentation.navigation.Screen
import com.example.classschedule.R


@Composable
fun ProfileScreen(
    onLogoutClick: (Screen) -> Unit
) {
    val viewModel: ProfileViewModel = hiltViewModel()
    ProfileView(
        viewModel = viewModel,
        onLogoutClick = onLogoutClick
    )
}

@Composable
fun ProfileView(
    viewModel: ProfileViewModel,
    onLogoutClick: (Screen) -> Unit
) {
    val user = viewModel.user.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. Аватарка (Круглая иконка)
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color.LightGray, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 2. Имя и Фамилия
        Text(
            text = "${user.value.name} ${user.value.surname}",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        // 3. Роль (Badge)
        Surface(
            color = MaterialTheme.colorScheme.primaryContainer,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(
                text = "Student",
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                style = MaterialTheme.typography.labelMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 4. Почта
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.Email,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = user.value.email, color = Color.Gray)
        }

        Spacer(modifier = Modifier.size(100.dp))

        // 5. Кнопка выхода
        Button(
            onClick = {
                viewModel.exitFromAcc()
                onLogoutClick(Screen.DefaultEntrance)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE57373)) // Красный оттенок
        ) {
            Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(stringResource(R.string.ExitFromAcc))
        }
    }
}

@Preview
@Composable
fun PreviewProfileScreen() {
    ProfileView(viewModel(), {})
}

