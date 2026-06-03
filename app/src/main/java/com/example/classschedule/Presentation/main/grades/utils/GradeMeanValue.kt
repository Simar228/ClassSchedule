package com.example.classschedule.Presentation.main.grades.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.classschedule.R

@Composable
fun GradeMeanValue(flattenedList: List<Map<Int, Int>>) {
    val meanGrades = mutableListOf<Float>()

    flattenedList.forEach { map ->
        val averageGrate = map.values.average().toFloat()
        if (!averageGrate.isNaN()) {
            meanGrades.add(averageGrate)
        }
    }
    val meanGrade = meanGrades.average().toFloat()
    val percentOfMeanGrade = (meanGrade - 1f) / 4f

    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(0.9f)

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(1f)
                    .padding(start = 20.dp, top = 10.dp, bottom = 10.dp),

                contentAlignment = Alignment.CenterStart
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .wrapContentHeight()
                ) {
                    Text(
                        text = stringResource(R.string.yourAverageGrade),
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 25.sp,
                        lineHeight = 24.sp
                    )
                    Text(
                        text = "%.2f".format(meanGrade),
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 60.sp
                    )
                    Text(
                        text = stringResource(R.string.great),
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 25.sp,

                        )
                }
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 10.dp, end = 10.dp, bottom = 10.dp)

            ) {

                CircularProgressIndicator(
                    progress = { percentOfMeanGrade },
                    modifier = Modifier
                        .size(width = 130.dp, height = 130.dp),
                    color = Color(0xFF00E676),
                    strokeWidth = 18.dp,
                    trackColor = MaterialTheme.colorScheme.background,
                    strokeCap = StrokeCap.Round,
                )

                Text(
                    text = "${(percentOfMeanGrade * 100).toInt()}%",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    fontSize = 38.sp
                )
            }
        }
    }
}


@Composable
@Preview
fun PreviewGradeMeanValue() {
    GradeMeanValue(List(31) { index ->
        when (index) {
            9 -> mapOf(1 to 5)
            10 -> mapOf(
                3 to 2,
                4 to 5,
                5 to 3,
                2 to 5,
                1 to 3,
                13 to 5
            )

            else -> emptyMap()
        }
    }
    )
}