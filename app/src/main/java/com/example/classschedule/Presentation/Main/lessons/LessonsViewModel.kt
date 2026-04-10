package com.example.classschedule.Presentation.Main.lessons

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.classschedule.Data.repository.LessonsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class LessonsViewModel @Inject constructor(
    // Все зависимости (репозитории) должны быть ТУТ в скобках
    private val lessonsRepository: LessonsRepository
) : ViewModel(){


    val lessons = lessonsRepository.lessonsState

    fun getLesson(date: Int){
        viewModelScope.launch(Dispatchers.IO) {
        lessonsRepository.getLesson(date)
        }
        return
    }
    val calendar = java.util.Calendar.getInstance()
    val daysInMonth = calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH)
    val dayOfMonth = calendar.get(java.util.Calendar.DAY_OF_MONTH)
    val dayOfWeek = (calendar.get(java.util.Calendar.DAY_OF_WEEK))

}