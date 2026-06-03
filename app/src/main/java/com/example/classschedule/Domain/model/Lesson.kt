package com.example.classschedule.Domain.model

data class Lesson(
    val date: Int,
    val lessonId: Int,
    val lessonName: String,
    val lessonTopic: String,
    val lessonHomeWork: String,
    val grade: Int?
)