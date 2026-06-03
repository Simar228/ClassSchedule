package com.example.classschedule.Domain.usecase.lesson


import com.example.classschedule.Domain.model.Lesson
import com.example.classschedule.Domain.repository.LessonsRepository
import javax.inject.Inject

class GetLessonUseCase @Inject constructor(
    private val lessonsRepository: LessonsRepository
) {
    suspend operator fun invoke(date: Int): Result<List<Lesson>> {
        return lessonsRepository.getLesson(date)
    }
}