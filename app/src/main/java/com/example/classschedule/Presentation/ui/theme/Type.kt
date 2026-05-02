package com.example.classschedule.Presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.classschedule.R

// Set of Material typography styles to start with
val MainFontFamily = FontFamily(
    Font(R.font.main_font_family, FontWeight.Normal),
    Font(R.font.small_font_family, FontWeight.Light),
    Font(R.font.semi_bold_font_family, FontWeight.SemiBold)
)
val Typography = Typography(
    // Основной текст (Body)
    // В iOS это "Body". Используется для длинных текстов, описаний и основного контента.
    bodyLarge = TextStyle(
        fontFamily = MainFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    // Крупные акцентные заголовки (Large Title)
    // В iOS это огромные заголовки в начале разделов
    titleLarge = TextStyle(
        fontFamily = MainFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 34.sp,
        lineHeight = 72.sp,
        letterSpacing = 0.sp
    ),

    // Подзаголовки или названия разделов (Headline / Subheadline)
    // Подходит для названий карточек или жирных заголовков внутри контента.
    titleMedium = TextStyle(
        fontFamily = MainFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 24.sp
    ),

    // Текст на кнопках и элементах управления (Call to Action)
    // В iOS кнопки обычно имеют вес Medium или Semibold, чтобы выделяться.
    labelLarge = TextStyle(
        fontFamily = MainFontFamily,
        fontWeight = FontWeight.Normal, // Для iOS лучше Medium (500), чем Normal
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),

    // Очень мелкий вспомогательный текст (Caption / Footnote)
    // Используется для примечаний, копирайтов или подписей под иконками в таб-баре.
    labelSmall = TextStyle(
        fontFamily = MainFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    )

