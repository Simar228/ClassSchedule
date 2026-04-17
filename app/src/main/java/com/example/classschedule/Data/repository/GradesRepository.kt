package com.example.classschedule.Data.repository

import com.example.classschedule.Data.dto.GradeDto
import com.example.classschedule.Data.dto.ToGradeList
import com.example.classschedule.Data.dto.ToPairList
import com.example.classschedule.Domain.dao.UserDao
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.security.Key
import javax.inject.Inject

class GradesRepository @Inject constructor(
    private val userDao: UserDao,
    private val supabaseClient: SupabaseClient
) {
    suspend fun getGrades() : Result<List<Map<Int, Int>>> {
        return runCatching {
            val userId = userDao.getUser()?.id ?: throw Exception("user_not_found")


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
            val grades =  gradesDto.map { it.ToGradeList() }

            grades.ToPairList()
        }
    }
}