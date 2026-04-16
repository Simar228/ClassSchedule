package com.example.classschedule.Domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.classschedule.Data.dto.GradeDto
import com.example.classschedule.Data.dto.LessonDto

@Entity
data class Lesson(
    @PrimaryKey val date : Int,
    val lessonId : Int,
    val lessonName : String,
    val lessonTopic : String,
    val lessonHomeWork : String,
    val grade : Int?
)



fun LessonDto.toLesson() : Lesson{
    return Lesson(
        date = this.date,
        lessonName = this.subjects.title,
        lessonTopic = this.lessonTopic,
        lessonHomeWork = this.lessonHomeWork,
        lessonId = this.lessonId,
        grade = this.grades.firstOrNull()?.value
    )
}

