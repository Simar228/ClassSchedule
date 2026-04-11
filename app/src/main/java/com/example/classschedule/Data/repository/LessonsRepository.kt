package com.example.classschedule.Data.repository

import android.util.Log
import com.example.classschedule.Data.dto.GradeDto
import com.example.classschedule.Data.dto.LessonDto
import com.example.classschedule.Domain.dao.UserDao
import com.example.classschedule.Domain.entity.Lesson
import com.example.classschedule.Domain.entity.toLesson

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Collections.emptyList
import javax.inject.Inject

class LessonsRepository@Inject constructor(
    val supabaseClient: SupabaseClient,
    val userDao: UserDao,

) {
    private val _lessonsState: MutableStateFlow<List<Lesson>> = MutableStateFlow<List<Lesson>>(emptyList())
    val lessonsState: StateFlow<List<Lesson>> = _lessonsState.asStateFlow()
    suspend fun getLesson(date: Int): Result<Unit> {
        return runCatching {
                val userId = userDao.getUser()?.id
                val lessons = supabaseClient.from("lesson_topic").select(
               columns = Columns.raw("""
                   *, subjects(title),
                   grades(value).filter(student_id.eq.'$userId')
                    """)
           ){
                    filter {
                        LessonDto::date eq date
                    }
                }.decodeList<LessonDto>()
                _lessonsState.value = lessons.map { it.toLesson() }
        }
    }
}
