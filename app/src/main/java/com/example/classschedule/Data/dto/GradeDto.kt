package com.example.classschedule.Data.dto

import com.example.classschedule.Domain.entity.Grade
import com.example.classschedule.Domain.entity.Lesson
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


fun GradeDto.ToGradeList() : Grade {
    return Grade(
        value = this.value,
        date = this.lessonTopic?.date ?: 777,
        subjectId = this.lessonTopic?.lessonId ?: 888
    )

}

fun List<Grade>.ToPairList() : List<Map<Int, Int>>{
    val pairGradeList = MutableList(31) { mapOf<Int, Int>() }
    this.forEach { grade ->
        val index = grade.date - 1
        if (index in 0..30){
            pairGradeList[index] = pairGradeList[index] +( grade.subjectId to grade.value)
        }

    }
    return pairGradeList
}
