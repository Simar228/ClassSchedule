package com.example.classschedule.Presentation.main.grades

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.component1
import androidx.core.graphics.component2
import com.example.classschedule.Presentation.main.grades.utils.GradeCard


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GradesScreenv2(grades: List<Map<Int, Int>>){
    val flattenedList = grades.flatMapIndexed { dayIndex, gradesMap ->
        gradesMap.map{ (lessonId, gradeValue) ->
            lessonId to (dayIndex to gradeValue)
        }
    }.groupBy({ it.first }, { it.second })
        .mapValues { it.value.toMap() }

    FlowRow(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        flattenedList.forEach { (index, map) ->
            GradeCard(
                grades = map,
                idLesson = index
            )
        }
    }


}

@Preview
@Composable
fun PreviewGradesScreenv2(){
    GradesScreenv2(List(31) { index ->
        when (index) {
            9 -> mapOf(1 to 5) // SingletonMap (size = 1)
            10 -> mapOf(
                3 to 2,
                4 to 5,
                5 to 3,
                2 to 5,
                1 to 3,
                13 to 5
            ) // LinkedHashMap (size = 6)
            else -> emptyMap() // Остальные пустые (size = 0)
        }
    })
}