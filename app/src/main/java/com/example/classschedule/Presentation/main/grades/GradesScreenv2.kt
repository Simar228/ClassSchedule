package com.example.classschedule.Presentation.main.grades

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.classschedule.Presentation.main.grades.utils.GradeCard
import com.example.classschedule.Presentation.main.grades.utils.GradeMeanValue
import com.example.classschedule.Presentation.main.grades.utils.UpperNavigation
import io.github.fletchmckee.liquid.LiquidState
import io.github.fletchmckee.liquid.liquefiable
import io.github.fletchmckee.liquid.rememberLiquidState


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GradesScreenv2(
    liquidState: LiquidState,
    grades: List<Map<Int, Int>>,
    navController: NavController
) {
    val flattenedList = grades.flatMapIndexed { dayIndex, gradesMap ->
        gradesMap.map { (lessonId, gradeValue) ->
            lessonId to (dayIndex to gradeValue)
        }
    }.groupBy({ it.first }, { it.second })
        .mapValues { it.value.toMap() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .liquefiable(liquidState),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            FlowColumn(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                UpperNavigation() {
                    navController.popBackStack()
                }
                GradeMeanValue(grades)
                flattenedList.forEach { (index, map) ->
                    GradeCard(
                        grades = map,
                        idLesson = index
                    )
                }
            }
        }
    }

}

@Preview
@Composable
fun PreviewGradesScreenv2() {
    GradesScreenv2(
        rememberLiquidState(),
        List(31) { index ->
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
        },
        rememberNavController()
    )
}