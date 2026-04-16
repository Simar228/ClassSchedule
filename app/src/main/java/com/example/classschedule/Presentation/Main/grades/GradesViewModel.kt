package com.example.classschedule.Presentation.Main.grades

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.classschedule.Data.dto.GradeDto
import com.example.classschedule.Data.dto.ToGradeList
import com.example.classschedule.Data.dto.ToPairList
import com.example.classschedule.Domain.dao.UserDao
import com.example.classschedule.Domain.entity.Grade
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GradesViewModel @Inject constructor(
    private val supabaseClient: SupabaseClient,
    private val userDao: UserDao
) : ViewModel() {



    suspend fun getGrades() : List<Map<Int, Int>>{
        val userId = userDao.getUser()?.id ?: return emptyList<Map<Int, Int>>()


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
        return grades.ToPairList()
    }
}

