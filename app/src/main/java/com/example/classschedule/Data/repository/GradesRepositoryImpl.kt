package com.example.classschedule.Data.repository

import com.example.classschedule.Data.dto.GradeDto
import com.example.classschedule.Data.dao.UserDao
import com.example.classschedule.Data.mapper.toDomain
import com.example.classschedule.Data.mapper.toPairList
import com.example.classschedule.Domain.model.Grade
import com.example.classschedule.Domain.provider.CurrentUserProvider
import com.example.classschedule.Domain.repository.GradesRepository
import com.example.classschedule.Domain.usecase.room.GetUserUseCase
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import javax.inject.Inject

class GradesRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient,
    private val currentUserProvider: CurrentUserProvider
) : GradesRepository {
    override suspend fun getGrades() : Result<List<Map<Int, Int>>> {
        return runCatching {
            val userId = currentUserProvider.getCurrentUserId() ?: throw Exception("user_not_found")


            val gradesDto =  supabaseClient.from("grades").select(
                columns = Columns.raw("""
                    *, lesson_topic(lesson_id, date)
                """
                )
            ) {

                filter {
                    eq("student_id", userId)
                }
            }.decodeList<GradeDto>()
            val grades =  gradesDto.map { it.toDomain() }

            grades.toPairList()
        }
    }

    override suspend fun addGrade(grade: Grade): Result<Unit> {
        TODO("Not yet implemented")
    }
}