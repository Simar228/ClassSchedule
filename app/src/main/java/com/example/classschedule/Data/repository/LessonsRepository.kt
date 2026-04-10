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
            try {


                val oldLessons = supabaseClient.from("lesson_topic").select(

               columns = Columns.raw("""
                   *, subjects(title)
                    """)
           ){
                    filter {
                        LessonDto::date eq date
                    }
                }.decodeList<LessonDto>()
                val newLessons = oldLessons.map {
                    it.toLesson()
                }


                val grades = try {
                    val userId = userDao.getUser()?.id
                    if (userId != null) {
                        supabaseClient.from("grades").select {
                            filter {
                                GradeDto::studentId eq userId
                                GradeDto::date eq date
                            }
                        }.decodeList<GradeDto>()
                    } else emptyList()
                } catch (e: Exception) {
                    Log.d("gradesE", e.toString())
                    emptyList<GradeDto>()
                }
                val gradesMap = grades.associateBy { it.lessonId }
                val updatedLessons = newLessons.map { lesson ->
                    val gradeForThisLesson = gradesMap[lesson.lessonId]?.grade
                    lesson.copy(grade = gradeForThisLesson)
                }
                _lessonsState.value = updatedLessons.toList()

            } catch (e: Exception) {
                Log.d("LessonsE", e.toString())
            }
        }
    }
}
