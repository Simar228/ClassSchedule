package com.example.classschedule.Presentation.main.lessons

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.classschedule.Presentation.ui.utils.EmptyLessonCard
import com.example.classschedule.Presentation.ui.utils.LessonCard
import io.github.fletchmckee.liquid.LiquidState
import io.github.fletchmckee.liquid.liquefiable
import io.github.fletchmckee.liquid.rememberLiquidState


@Composable
fun LessonsScreen(liquidState: LiquidState) {
    val viewModel: LessonsViewModel = hiltViewModel()
    LessonsView(viewModel = viewModel, liquidState = liquidState)
}



@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
private fun LessonsView(
    viewModel: LessonsViewModel,
    liquidState: LiquidState
) {


    var isLoadingLessonList by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val currentLesson = viewModel.currentLesson
    //с какой даты начать
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = viewModel.dayOfMonth - 1
    )
    // Настройка логики "примагничивания" (snapping) элементов при скролле
    val snappingLayout = remember(listState) {
        SnapLayoutInfoProvider(listState)
    }
    // Поведение, которое заставляет список плавно "доезжать" до ближайшего элемента
    val flingBehavior = rememberSnapFlingBehavior(snappingLayout)
    // Получение параметров экрана (ширина) для расчетов
    val configuration = LocalConfiguration.current
    // Вычисление доступной ширины экрана за вычетом 85dp (вероятно, для учета боковых отступов)
    val screenWidth = configuration.screenWidthDp.dp - 85.dp
    // Фиксированная ширина одного элемента (кнопки с числом)
    val itemWidth = 70.dp
    // Расчет пустого пространства по бокам, чтобы элемент мог встать ровно по центру
    val horizontalPadding = (screenWidth - itemWidth) / 2
    var dayOfMonth = viewModel.dayOfMonth
    val localDensity = LocalDensity.current
    val fontSizeForCalendar = with(localDensity) {16.dp.toSp()}



    LazyColumn(
        modifier = Modifier
            .liquefiable(liquidState)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 100.dp, start = 40.dp, end = 40.dp, bottom = 30.dp),
                state = listState,
                flingBehavior = flingBehavior,
                contentPadding = PaddingValues(horizontal = horizontalPadding),
                horizontalArrangement = Arrangement.spacedBy(40.dp)
                ){
                items(31){_index ->
                    val index = _index + 1
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor =
                                if
                                    (index == dayOfMonth) MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.3f )
                        ),
                        enabled = !isLoadingLessonList,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.size(itemWidth),
                        onClick = {
                            viewModel.onDateSelected(index)
                            viewModel.getLesson(index)
                        }
                    ) {
                        Text(text = index.toString(),
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = fontSizeForCalendar)
                    }

                }
            }
        }
        if(viewModel.isLoading){
            items(5){
                EmptyLessonCard(liquidState)
            }
        }else {
            items(currentLesson) { lesson ->
                LessonCard(
                    liquidState = liquidState,
                    lessonName = lesson.lessonName,
                    lessonTopic = lesson.lessonTopic,
                    lessonHomeWork = lesson.lessonHomeWork,
                    grade = lesson.grade
                )
            }
        }
        item {
            Spacer(modifier = Modifier.size(130.dp))
        }

    }

}

@Composable
@Preview(showBackground = true)
private fun PreviewLessonsScreen(){
    LessonsView(viewModel(), liquidState = rememberLiquidState())
}