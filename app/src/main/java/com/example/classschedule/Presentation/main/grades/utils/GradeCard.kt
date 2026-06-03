package com.example.classschedule.Presentation.main.grades.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.classschedule.Domain.constants.SubjectEnum
import com.example.classschedule.Presentation.ui.theme.grades
import com.example.classschedule.R
import kotlin.math.roundToInt


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GradeCard(grades: Map<Int, Int>, idLesson: Int) {
    val averageGrade = grades.values.average().roundToInt()
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .wrapContentHeight()
    ) {


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 20.dp, top = 10.dp, bottom = 10.dp)
                    .weight(1f)
            ) {
                Row(
                    modifier = Modifier.padding(bottom = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        painter = painterResource(
                            SubjectEnum.getById(idLesson)?.icon ?: R.drawable.error
                        ),
                        contentDescription = null,
                        modifier = Modifier.size(MaterialTheme.typography.titleLarge.fontSize.value.dp * 1.3f)

                    )
                    Text(
                        text = SubjectEnum.getById(idLesson)?.title ?: "504",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.horizontalScroll(rememberScrollState())

                    )
                }
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth(),
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
                    .wrapContentWidth()
                    .padding(15.dp)
                    .size(60.dp)
                    .background(
                        color = MaterialTheme.colorScheme.grades(averageGrade),
                        shape = RoundedCornerShape(30)
                    )


            ) {
                Text(

                    text = averageGrade.toString(),
                    color = Color.White,
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = with(LocalDensity.current) { 30.dp.toSp() },
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