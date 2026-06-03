package com.example.classschedule.Presentation.main.grades.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.classschedule.Presentation.ui.theme.grades


@Composable
fun GradeIcon(date: Int, grade: Int) {
    val backgroundColor = MaterialTheme.colorScheme.grades(grade)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(30.dp)
                .background(color = backgroundColor, shape = RoundedCornerShape(6.dp))


        ) {
            Text(
                text = grade.toString(),
                fontSize = with(LocalDensity.current){ 16.dp.toSp()},
                style = MaterialTheme.typography.labelLarge,
                color = Color.White,
            )
        }
        Text(
            text = (date + 1).toString(),
            fontSize = 14.sp,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview
@Composable
fun PreviewGradeIcon() {
    GradeIcon(10, 3)
}