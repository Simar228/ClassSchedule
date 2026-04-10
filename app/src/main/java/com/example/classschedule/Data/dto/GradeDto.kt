package com.example.classschedule.Data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class GradeDto (
    @SerialName("value")val grade: Int,
    @SerialName("subject_id")val lessonId : Int,
    @SerialName("student_id") val studentId: String,
    @SerialName("date") val date: Int
)