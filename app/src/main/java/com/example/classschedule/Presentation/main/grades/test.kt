package com.example.classschedule.Presentation.main.grades

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradesScreen() {
    var selectedQuarter by remember { mutableStateOf(1) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Оценки") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Табы для четвертей
            QuarterTabs(
                selectedQuarter = selectedQuarter,
                onQuarterSelected = { selectedQuarter = it }
            )

            // Список оценок
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(getSubjectsWithGrades(selectedQuarter)) { subject ->
                    SubjectGradeCard(subject)
                }
            }
        }
    }
}

@Composable
fun QuarterTabs(selectedQuarter: Int, onQuarterSelected: (Int) -> Unit) {
    ScrollableTabRow(
        selectedTabIndex = selectedQuarter - 1,
        containerColor = MaterialTheme.colorScheme.surface,
        edgePadding = 0.dp
    ) {
        (1..4).forEach { quarter ->
            Tab(
                selected = selectedQuarter == quarter,
                onClick = { onQuarterSelected(quarter) },
                text = { Text("$quarter четверть") }
            )
        }
    }
}

@Composable
fun SubjectGradeCard(subject: SubjectGrades) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Название предмета и четвертная оценка
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = subject.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                // Четвертная оценка
                Surface(
                    shape = CircleShape,
                    color = getGradeColor(subject.quarterGrade),
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = subject.quarterGrade.toString(),
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Список оценок с датами
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                subject.grades.forEach { grade ->
                    GradeChip(grade)
                }
            }
        }
    }
}

@Composable
fun GradeChip(grade: Grade) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = getGradeColor(grade.value),
            modifier = Modifier.size(36.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = grade.value.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        Text(
            text = grade.date,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

fun getGradeColor(grade: Int): Color {
    return when (grade) {
        5 -> Color(0xFF4CAF50) // Зеленый
        4 -> Color(0xFF2196F3) // Синий
        3 -> Color(0xFFFF9800) // Оранжевый
        2 -> Color(0xFFF44336) // Красный
        else -> Color.Gray
    }
}

// Модели данных
data class SubjectGrades(
    val name: String,
    val quarterGrade: Int,
    val grades: List<Grade>
)

data class Grade(
    val value: Int,
    val date: String
)

// Пример данных
fun getSubjectsWithGrades(quarter: Int): List<SubjectGrades> {
    return listOf(
        SubjectGrades(
            name = "Математика",
            quarterGrade = 5,
            grades = listOf(
                Grade(5, "12.01"),
                Grade(5, "15.01"),
                Grade(4, "19.01"),
                Grade(5, "22.01"),
                Grade(5, "26.01")
            )
        ),
        SubjectGrades(
            name = "Русский язык",
            quarterGrade = 4,
            grades = listOf(
                Grade(4, "13.01"),
                Grade(5, "16.01"),
                Grade(4, "20.01"),
                Grade(3, "23.01")
            )
        ),
        SubjectGrades(
            name = "Физика",
            quarterGrade = 4,
            grades = listOf(
                Grade(4, "14.01"),
                Grade(4, "18.01"),
                Grade(5, "21.01"),
                Grade(4, "25.01")
            )
        ),
        SubjectGrades(
            name = "История",
            quarterGrade = 5,
            grades = listOf(
                Grade(5, "11.01"),
                Grade(5, "17.01"),
                Grade(5, "24.01")
            )
        )
    )
}

