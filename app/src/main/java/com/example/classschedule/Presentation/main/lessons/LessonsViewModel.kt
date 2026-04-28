package com.example.classschedule.Presentation.main.lessons

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.classschedule.Data.repository.LessonsRepository
import com.example.classschedule.Domain.entity.Lesson
import com.example.classschedule.Presentation.util.suppabaseErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LessonsViewModel @Inject constructor(
    private val lessonsRepository: LessonsRepository
) : ViewModel() {


    private var fetchJob: Job? = null
    private var _currentLesson = mutableStateListOf<Lesson>()
    val currentLesson = _currentLesson
    val calendar = java.util.Calendar.getInstance()
    var dayOfMonth = calendar.get(java.util.Calendar.DAY_OF_MONTH)

    init {
        getLesson(dayOfMonth)
    }

    private suspend fun getLessonViewModel(date: Int): List<Lesson> {
        val lessons = lessonsRepository.getLesson(date)
        lessons.suppabaseErrorHandler(tag = "lessons") { }
        return lessons.getOrElse { emptyList() }
    }

    fun getLesson(date: Int) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch(context = Dispatchers.IO) {
            val lessons = getLessonViewModel(date).toMutableStateList()
            _currentLesson.clear()
            _currentLesson.addAll(lessons)
        }
    }


}