package com.example.classschedule.Presentation.main.grades

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.classschedule.Presentation.main.lessons.utils.SubjectsNameColumn

@Composable
fun GradesScreen(){
    val viewModel : GradesViewModel = hiltViewModel<GradesViewModel>()
    ViewGradeScreen(viewModel)

}


@Composable
fun ViewGradeScreen(
    viewModel: GradesViewModel
){
    val gradesList by viewModel.grades.collectAsStateWithLifecycle()

    SubjectsNameColumn(
        gradesList
    )

}




