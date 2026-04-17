package com.example.classschedule.Presentation.Main.lessons

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.classschedule.Data.repository.LessonsRepository
import com.example.classschedule.Domain.entity.Lesson
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class LessonsViewModel @Inject constructor(
    private val lessonsRepository: LessonsRepository
) : ViewModel(){


    private var _currentLesson = mutableStateListOf<Lesson>()
    val currentLesson = _currentLesson
    val calendar = java.util.Calendar.getInstance()
    val dayOfMonth = calendar.get(java.util.Calendar.DAY_OF_MONTH)

    private suspend fun getLessonViewModel(date: Int) : List<Lesson>{
        return lessonsRepository.getLesson(date)
            .onFailure { exception ->
                Log.e("Get Lessons", exception.toString())
            }
            .getOrElse { emptyList() }
        }

    fun getLesson(date: Int){
        viewModelScope.launch(context = Dispatchers.IO) {
            val lessons = getLessonViewModel(date).toMutableStateList()
            _currentLesson.clear()
            _currentLesson.addAll(lessons)
        }
    }




}