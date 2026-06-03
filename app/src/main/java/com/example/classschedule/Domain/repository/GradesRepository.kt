package com.example.classschedule.Domain.repository

import com.example.classschedule.Domain.model.Grade

interface GradesRepository {

    suspend fun getGrades(): Result<List<Map<Int, Int>>>
    suspend fun addGrade(grade: Grade): Result<Unit>
}