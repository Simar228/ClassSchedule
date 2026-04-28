package com.example.classschedule.Presentation.ui.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.classschedule.R


@Composable
fun LessonCard(
    lessonName: String,
    lessonTopic: String,
    lessonHomeWork: String,
    grade: Int?,
) {
    val result = grade ?: "-"
    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(width = 340.dp, height = 260.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2D2D2D),
            contentColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 25.dp, bottom = 25.dp, start = 15.dp, end = 15.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(0.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                Column {
                    Text(
                        text = lessonName.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        modifier = Modifier
                            .width(250.dp)
                            .padding(top = 15.dp),
                        text = lessonTopic,
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White.copy(alpha = 0.7f),
                    )
                }


                Surface(
                    color = Color.White.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "${stringResource(R.string.HomeWork)} $lessonHomeWork",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelLarge,
                        color = Color(0xFF81D4FA)
                    )
                }
            }


            Text(

                text = result.toString(),
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Black,
                color = when(result){
                    5 -> Color.Green
                    4 -> Color.Blue
                    3 -> Color.Yellow
                    2 -> Color.Red
                    1 -> Color.Black
                    else -> {

                        Color.White}
                },
                modifier = Modifier.align(Alignment.TopEnd)
            )
        }
    }
}



@Composable
@Preview
fun PrevierwLessonsCard(){
    LessonCard(
        lessonName = "404",
        lessonTopic = "Правописание а и уyyyyyyy yyyyyyyyyeeeeeeeee eeeeeeeeeeeeeee",
        lessonHomeWork = "Диктант на странице 8 и номера с 233 по 236",
        grade = 5
    )
}