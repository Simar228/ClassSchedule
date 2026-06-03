package com.example.classschedule.Data.dto

import com.example.classschedule.Domain.model.Grade
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class GradeDto (
    @SerialName("value")val value: Int,
    @SerialName("student_id")val studentId: String? = null,
    @SerialName("lesson_topic") val lessonTopic : LessonGradeDto? = null,

)
@Serializable
data class LessonGradeDto(
    @SerialName("lesson_id")val lessonId: Int,
    @SerialName("date") val date: Int
)


