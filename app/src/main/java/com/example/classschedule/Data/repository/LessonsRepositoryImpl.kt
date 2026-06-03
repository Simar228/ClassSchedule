package com.example.classschedule.Data.repository

import com.example.classschedule.Data.dto.LessonDto
import com.example.classschedule.Data.dao.UserDao

import com.example.classschedule.Data.mapper.toLesson
import com.example.classschedule.Data.provider.CurrentUserProviderImpl
import com.example.classschedule.Domain.model.Lesson
import com.example.classschedule.Domain.repository.LessonsRepository
import com.example.classschedule.Domain.usecase.room.GetUserUseCase
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import javax.inject.Inject

class LessonsRepositoryImpl @Inject constructor(
    val supabaseClient: SupabaseClient,
    val currentUserProviderImpl: CurrentUserProviderImpl

): LessonsRepository {

    override suspend fun getLesson(date: Int): Result<List<Lesson>> {
        return runCatching {
            val userId = currentUserProviderImpl.getCurrentUserId()
            val lessons = supabaseClient.from("lesson_topic").select(
                columns = Columns.raw(
                    """
                   *, subjects(title),
                   grades(value).filter(student_id.eq.'$userId')
                    """
                )
            ) {
                filter {
                    LessonDto::date eq date
                }
            }.decodeList<LessonDto>()
            lessons.map {
                it.toLesson()
            }
        }
    }
}

