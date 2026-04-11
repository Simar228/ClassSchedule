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
    private val lessonsRepository: LessonsRepository
) : ViewModel(){


    val lessons = lessonsRepository.lessonsState
    val calendar = java.util.Calendar.getInstance()
    val dayOfMonth = calendar.get(java.util.Calendar.DAY_OF_MONTH)

    fun getLesson(date: Int){
        viewModelScope.launch(Dispatchers.IO) {
        val getLessons = lessonsRepository.getLesson(date)
            getLessons.onFailure{ e ->
                Log.e("Get Lessons", e.toString())
            }
        }
        return
    }



}