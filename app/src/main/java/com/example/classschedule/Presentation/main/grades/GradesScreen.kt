package com.example.classschedule.Presentation.main.grades

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.classschedule.Presentation.main.lessons.utils.SubjectsNameColumn
import io.github.fletchmckee.liquid.LiquidState

@Composable
fun GradesScreen(
    liquidState: LiquidState,
) {
    val viewModel: GradesViewModel = hiltViewModel<GradesViewModel>()
    ViewGradeScreen(liquidState, viewModel)

}


@Composable
fun ViewGradeScreen(
    liquidState: LiquidState,
    viewModel: GradesViewModel
) {
    val gradesList by viewModel.grades.collectAsStateWithLifecycle()

    GradesScreenv2(
        gradesList,
//        liquidState
    )

}




