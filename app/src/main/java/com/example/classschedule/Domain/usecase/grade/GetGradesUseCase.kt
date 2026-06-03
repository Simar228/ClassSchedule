package com.example.classschedule.Domain.usecase.grade

import com.example.classschedule.Domain.repository.GradesRepository
import javax.inject.Inject

class GetGradesUseCase @Inject constructor(
    private val gradesRepository: GradesRepository
) {
    suspend operator fun invoke(): Result<List<Map<Int, Int>>> {
        return gradesRepository.getGrades()
    }
}