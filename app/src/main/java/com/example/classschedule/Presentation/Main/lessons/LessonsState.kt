package com.example.classschedule.Presentation.Main.lessons

import com.example.classschedule.Domain.entity.Lesson


data class LessonsState(
    val lessons : Map<Int, List<Lesson>>
)

sealed interface LessonsEvent{
    data class AddLessons(val date: Int, val lessonsList: List<Lesson>)
}