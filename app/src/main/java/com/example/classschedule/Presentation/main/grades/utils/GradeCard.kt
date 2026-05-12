package com.example.classschedule.Presentation.main.grades.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.classschedule.Domain.constants.SubjectEnum
import com.example.classschedule.Presentation.ui.theme.grades
import kotlin.math.roundToInt


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GradeCard(grades: Map<Int, Int>, idLesson: Int) {
    val averageGrate = grades.values.average().roundToInt()
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .wrapContentHeight()
            .clip(shape = RoundedCornerShape(20.dp))

    ) {


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column() {
                Text(
                    text = SubjectEnum.getById(idLesson)?.title ?: "504",
                    modifier = Modifier.padding(start = 15.dp, top = 10.dp),
                    style = MaterialTheme.typography.titleLarge,
                )
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    grades.forEach { (key, value) ->
                        GradeIcon(key, value)
                    }
                }
            }
            Box(

                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(15.dp)
                    .size(60.dp)
                    .background(
                        color = MaterialTheme.colorScheme.grades(averageGrate),
                        shape = RoundedCornerShape(30)
                    )


            ) {
                Text(

                    text = averageGrate.toString(),
                    color = Color.White,
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = with(LocalDensity.current){30.dp.toSp()},
                    modifier = Modifier
                        .padding(10.dp)


                )
            }

        }

    }
}


@Preview
@Composable
fun PreviewGradeCard() {
    GradeCard(
        mapOf(
            1 to 5,
            10 to 5,
            3 to 5,
            2 to 5,
            8 to 5,
            9 to 5,
            13 to 5,
            14 to 5,
            35 to 5,
        ), 2
    )

}