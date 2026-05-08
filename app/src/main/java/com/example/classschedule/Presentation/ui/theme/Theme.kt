package com.example.classschedule.Presentation.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF1C1C1E), //Основные кнопки, важные иконки или заголовки
    secondary = Color(0xFF1C1C1E), //Менее важные кнопки или элементы, которые должны выделяться на темном фоне.
    tertiary = Color(0xFFFF0000), //Акценты для нейтральных элементов (например, выбранные даты в календаре).
    error = ErrorColor, //Текст «Неверный пароль», красные иконки, границы полей при ошибке.
    background = Color(0xFFF2F2F7), //Самый задний фон всего экрана.
    surface = Color(0xFFF2F2F7), //Карточки расписания, белые поля ввода, всплывающие меню.
    onPrimary = Color.White, //Текст и иконки внутри главных кнопок (Button).
    onSecondary = Color.White, //Текст на второстепенных элементах (маленькие кнопки, теги, значки уведомлений).
    onTertiary = Color.White, //Цвет для контента, который лежит поверх цветов secondary и tertiary.
    onBackground = Color(0xFF1C1B1F), //Весь основной текст на экране
    onSurface = Color(0xFF1C1B1F), //Текст внутри карточек, диалоговых окон и полей ввода (TextField).
    onSurfaceVariant = Color(0xFF5856D6),

)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1C1C1E), //Основные кнопки, важные иконки или заголовки
    secondary = Color(0xFF1C1C1E), //Менее важные кнопки или элементы, которые должны выделяться на темном фоне.
    tertiary = Color(0xFFFF0000), //Акценты для нейтральных элементов (например, выбранные даты в календаре).
    error = ErrorColor, //Текст «Неверный пароль», красные иконки, границы полей при ошибке.
    background = Color(0xFFF2F2F7), //Самый задний фон всего экрана.
    surface = Color(0xFFF2F2F7), //Карточки расписания, белые поля ввода, всплывающие меню.
    onPrimary = Color.White, //Текст и иконки внутри главных кнопок (Button).
    onSecondary = Color.White, //Текст на второстепенных элементах (маленькие кнопки, теги, значки уведомлений).
    onTertiary = Color.White, //Цвет для контента, который лежит поверх цветов secondary и tertiary.
    onBackground = Color(0xFF1C1B1F), //Весь основной текст на экране
    onSurface = Color(0xFF1C1B1F), //Текст внутри карточек, диалоговых окон и полей ввода (TextField).
    onSurfaceVariant = Color(0xFF5856D6),

)


val ColorScheme.darkError : Color
    @Composable
    get() = if (!isSystemInDarkTheme()) Color(0xFFB00020) else Color(0xFFB00020)

val ColorScheme.link : Color
    @Composable
    get() = if (!isSystemInDarkTheme()) Color(0xFF007AFF) else Color(0xFF007AFF)




@Composable
fun ClassScheduleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}