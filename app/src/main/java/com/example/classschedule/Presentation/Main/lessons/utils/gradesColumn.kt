package com.example.classschedule.Presentation.Main.lessons.utils

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.classschedule.Domain.constants.SubjectEnum


val arrangmentBetweenCard = 8.dp
val sizeCard = 50.dp
@Preview(showBackground = true)
@Composable
fun SubjectsNameColumn( grades: List<Map<Int, Int>> = emptyList()) {
    val commonScrollState = rememberScrollState()
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        item {
            Column(
                modifier = Modifier
                    .width(300.dp)
                    .padding(16.dp)
                    .verticalScroll(commonScrollState),
                verticalArrangement = Arrangement.spacedBy(arrangmentBetweenCard)
            ) {
                Text(
                    text = "!Список предметов!",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                SubjectEnum.entries.forEach { subject ->
                    SubjectItem(subject,)
                }
            }
        }
        items(31) { index ->
            GradesColumn(grades.getOrElse(index) { emptyMap() }, index + 1, commonScrollState)

        }
        item {
            val meanGrade = grades.flatMap { it.entries }
                .groupBy ({it.key}, {it.value})
                .mapValues { it.value.average() }
            GradesColumn(meanGrade, commonScrollState)
        }
    }
}



@Composable
fun SubjectItem(subject: SubjectEnum) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .size(250.dp,sizeCard),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Иконка в будущем
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = subject.title.take(1),
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Название предмета
            Text(
                text = subject.title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

//для оценок обычного дня
@Composable
fun GradesColumn(grades: Map<Int, Int>, date: Int, scrollState: ScrollState){
    Column(modifier = Modifier
        .fillMaxHeight()
        .padding(top = 16.dp, bottom = 16.dp, end = 16.dp)
        .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(arrangmentBetweenCard)) {
        Text(text = date.toString(),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
     SubjectEnum.entries.forEachIndexed { index, subject ->
            val gradeValue = grades.getOrDefault(subject.id, "")
            Surface(modifier = Modifier,
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                tonalElevation = 2.dp
                ){
                Box(modifier = Modifier
                    .padding(16.dp)
                    .size(sizeCard),
                    contentAlignment = Alignment.Center) {
                    Text(text = gradeValue.toString())
                }
            }

        }

    }
}

//для средней четвертной оценки

@Composable
fun GradesColumn(grades: Map<Int, Double>, scrollState: ScrollState){
    Column(modifier = Modifier
        .fillMaxHeight()
        .padding(top = 16.dp, bottom = 16.dp, end = 16.dp)
        .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(arrangmentBetweenCard)) {
        Text(text = "Четвертная",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        SubjectEnum.entries.forEachIndexed { index, subject ->
            val gradeValue = grades.getOrDefault(subject.id, "")
            Surface(modifier = Modifier,
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                tonalElevation = 2.dp
            ){
                Box(modifier = Modifier
                    .padding(16.dp)
                    .size(sizeCard),
                    contentAlignment = Alignment.Center) {
                    Text(text = gradeValue.toString())
                }
            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGradesColumn(){


}