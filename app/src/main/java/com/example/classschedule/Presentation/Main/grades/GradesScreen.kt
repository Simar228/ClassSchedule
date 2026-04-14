package com.example.classschedule.Presentation.Main.grades

import android.content.ClipData
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.classschedule.Presentation.Main.lessons.utils.GradesColumn
import com.example.classschedule.Presentation.Main.lessons.utils.SubjectsNameColumn

@Composable
fun GradesScreen(){

    SubjectsNameColumn(List(31) {
        mapOf(
            1 to 1,
            2 to 2,
            5 to 5
        )
    }
    )
}




