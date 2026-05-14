package com.example.classschedule.Presentation.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.classschedule.Presentation.ui.theme.grades
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
            .wrapContentHeight()
            .fillMaxWidth(0.8f),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 25.dp, bottom = 25.dp, start = 15.dp, end = 15.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Column(modifier = Modifier.fillMaxWidth(0.9f)) {
                    Text(
                        text = lessonName,
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
                    modifier = Modifier
                        .padding(top = 25.dp)
                        .fillMaxWidth(0.9f)
                        .wrapContentHeight(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "${stringResource(R.string.HomeWork)} $lessonHomeWork",
                        modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }


            Text(

                text = result.toString(),
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.grades(grade),
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



