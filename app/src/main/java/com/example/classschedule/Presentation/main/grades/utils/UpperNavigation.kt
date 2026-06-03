package com.example.classschedule.Presentation.main.grades.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.classschedule.R


@Composable
fun UpperNavigation(
    popBackStack: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(top = 50.dp, bottom = 30.dp)
            .fillMaxWidth(0.9f),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(
            onClick = popBackStack,
        ){
            Icon(
                modifier = Modifier.size(28.dp),
                painter = painterResource(R.drawable.back_arrow),
                contentDescription = null,
            )
        }


        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(R.string.myGrades),
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .size(45.dp)
                .background(Color.LightGray, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(30.dp),
                tint = Color.Gray
            )
        }
    }
}

@Composable
@Preview
fun PreviewUpperNavigation() {
    UpperNavigation(){}
}