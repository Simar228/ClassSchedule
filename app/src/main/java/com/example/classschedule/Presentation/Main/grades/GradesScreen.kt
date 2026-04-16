package com.example.classschedule.Presentation.Main.grades

import android.content.ClipData
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import com.example.classschedule.Presentation.Main.lessons.utils.GradesColumn
import com.example.classschedule.Presentation.Main.lessons.utils.SubjectsNameColumn
import kotlinx.coroutines.async

@Composable
fun GradesScreen(){
    val viewModel : GradesViewModel = hiltViewModel<GradesViewModel>()
    viewGradeScreen(viewModel)

}


@Composable
fun viewGradeScreen(
    viewModel: GradesViewModel
){
    var gradesList by remember { mutableStateOf<List<Map<Int, Int>>>(emptyList())}
    LaunchedEffect(Unit) {
        val result = viewModel.getGrades()
        gradesList = result
    }
    SubjectsNameColumn(
        gradesList
    )

}




