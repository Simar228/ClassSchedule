package com.example.classschedule.Domain.usecase.grade

import com.example.classschedule.Domain.model.Grade
import com.example.classschedule.Domain.repository.GradesRepository

class AddGradeUseCase(
    private val gradesRepository: GradesRepository
) {
    suspend operator fun invoke(grade: Grade): Result<Unit> {
        if (grade.value !in 1..5) {
            return Result.failure(IllegalArgumentException("Grade must be between 1 and 5"))
        }
        return gradesRepository.addGrade(grade)
    }
}