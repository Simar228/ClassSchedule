package com.example.classschedule.Presentation.ui.utils

import androidx.compose.foundation.background
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
import androidx.compose.material3.ColorScheme
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
import com.example.classschedule.Presentation.ui.theme.shimmerBrush
import com.example.classschedule.R
import io.github.fletchmckee.liquid.LiquidState
import io.github.fletchmckee.liquid.liquefiable
import io.github.fletchmckee.liquid.rememberLiquidState


@Composable
fun LessonCard(
    liquidState: LiquidState,
    lessonName: String,
    lessonTopic: String,
    lessonHomeWork: String,
    grade: Int?,
) {
    val result = grade ?: "-"
    Card(
        modifier = Modifier
            .liquefiable(liquidState)
            .padding(8.dp)
            .size(width = 340.dp, height = 260.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
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
                    )
                    Text(
                        modifier = Modifier
                            .width(250.dp)
                            .padding(top = 15.dp),
                        text = lessonTopic,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    )
                }


                Surface(

                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "${stringResource(R.string.HomeWork)} $lessonHomeWork",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }


            Text(

                text = result.toString(),
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Black,
                color = when(result){
                    5 -> Color(0xFF34C759)
                    4 -> Color(0xFFFFCC00)
                    3 -> Color(0xFFFF9500)
                    2 -> Color(0xFFFF3B30)
                    1 -> Color.Black
                    else -> {

                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)}
                },
                modifier = Modifier.align(Alignment.TopEnd)
            )
        }
    }
}


@Composable
fun EmptyLessonCard(
    liquidState: LiquidState
){
    val brush = shimmerBrush()
    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(width = 340.dp, height = 260.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)

    ){
        Box(modifier = Modifier
            .liquefiable(liquidState)
            .fillMaxSize()
            .background(brush))
    }
}



@Composable
@Preview
fun PrevierwLessonsCard(){
    LessonCard(
        liquidState = rememberLiquidState(),
        lessonName = "404",
        lessonTopic = "Правописание а и уyyyyyyy yyyyyyyyyeeeeeeeee eeeeeeeeeeeeeee",
        lessonHomeWork = "Диктант на странице 8 и номера с 233 по 236",
        grade = 5
    )
}



