package com.example.classschedule.Data.dto

import android.icu.text.CaseMap
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class LessonDto(
    @SerialName("id") val id: Int,
    @SerialName("subjects")val subjects: SubjectNameDto,
    @SerialName("lesson_topic") val lessonTopic: String,
    @SerialName("lesson_home_work") val lessonHomeWork: String,
    @SerialName("lesson_id") val lessonId: Int,
    @SerialName("date") val date: Int,
    @SerialName("grades")val grades: List<GradeDto> = emptyList()

)
@Serializable
data class SubjectNameDto(
    val title: String
)

