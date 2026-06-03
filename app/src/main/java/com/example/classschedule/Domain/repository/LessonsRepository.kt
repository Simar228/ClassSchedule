package com.example.classschedule.Domain.repository


import com.example.classschedule.Domain.model.Lesson

interface LessonsRepository {

    suspend fun getLesson(date: Int): Result<List<Lesson>>

}