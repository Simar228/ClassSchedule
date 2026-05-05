package com.example.classschedule.Presentation.main.lessons

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.classschedule.Data.repository.LessonsRepository
import com.example.classschedule.Domain.entity.Lesson
import com.example.classschedule.Presentation.util.suppabaseErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

@HiltViewModel
class LessonsViewModel @Inject constructor(
    private val lessonsRepository: LessonsRepository
) : ViewModel() {


    private var fetchJob: Job? = null
    private var _currentLesson = mutableStateListOf<Lesson>()
    val currentLesson = _currentLesson
    val calendar = java.util.Calendar.getInstance()
    var isLoading by mutableStateOf(true)
    var dayOfMonth by mutableIntStateOf( calendar.get(java.util.Calendar.DAY_OF_MONTH))
        private set
    init {
        getLesson(dayOfMonth)
    }

    fun onDateSelected(newDate: Int) {
        dayOfMonth = newDate
        getLesson(newDate)
    }

    private suspend fun getLessonViewModel(date: Int): List<Lesson> {
        val lessons = lessonsRepository.getLesson(date)
        lessons.suppabaseErrorHandler(tag = "lessons") { }
        return lessons.getOrElse { emptyList() }
    }

    fun getLesson(date: Int) {
        fetchJob?.cancel()
        _currentLesson.clear()
        fetchJob = viewModelScope.launch(context = Dispatchers.IO) {
            isLoading = true
            val lessons = getLessonViewModel(date).toMutableStateList()
            withContext(Dispatchers.Main) {
                _currentLesson.addAll(lessons)
            }

            isLoading = false

        }
    }


}