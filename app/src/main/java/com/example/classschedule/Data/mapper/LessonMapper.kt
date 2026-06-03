package com.example.classschedule.Data.mapper

import com.example.classschedule.Data.dto.LessonDto
import com.example.classschedule.Domain.model.Lesson


fun LessonDto.toLesson() : Lesson {
    return Lesson(
        date = this.date,
        lessonName = this.subjects.title,
        lessonTopic = this.lessonTopic,
        lessonHomeWork = this.lessonHomeWork,
        lessonId = this.lessonId,
        grade = this.grades.firstOrNull()?.value
    )
}

