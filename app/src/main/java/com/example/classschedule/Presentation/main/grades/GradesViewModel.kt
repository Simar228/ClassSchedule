package com.example.classschedule.Presentation.main.grades

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.classschedule.Domain.usecase.grade.GetGradesUseCase
import com.example.classschedule.Presentation.util.supabaseErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GradesViewModel @Inject constructor(
    private val getGradesUseCase: GetGradesUseCase
) : ViewModel() {
    var fetchJob : Job? = null
    private val _grades = MutableStateFlow<List<Map<Int, Int>>>(emptyList())
    val grades = _grades.asStateFlow()


    init {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch(context = Dispatchers.IO) {
            getGrades()

        }
    }
    suspend fun getGrades(){
        val grades = getGradesUseCase()
        grades.supabaseErrorHandler(tag = "Grades") {

            val gradesList = grades
            _grades.value = gradesList.getOrElse { emptyList() }
        }
    }

}

