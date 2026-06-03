package com.example.classschedule.Data.mapper

import com.example.classschedule.Data.dto.GradeDto
import com.example.classschedule.Domain.model.Grade


fun GradeDto.toDomain(): Grade {
    return Grade(
        value = this.value,
        date = this.lessonTopic?.date ?: 777,
        subjectId = this.lessonTopic?.lessonId ?: 888
    )
}

fun List<Grade>.toPairList(): List<Map<Int, Int>> {
    val daysInMonth = 31
    val pairGradeList = MutableList(daysInMonth) { mapOf<Int, Int>() }
    this.forEach { grade ->
        val index = grade.date - 1
        if (index in 0..30) {
            pairGradeList[index] = pairGradeList[index] + (grade.subjectId to grade.value)
        }
    }
    return pairGradeList
}

