package com.example.classschedule.Domain.constants

import com.example.classschedule.R

enum class SubjectEnum(val id: Int, val title: String, val icon: Int) {
    RUSSIAN_LANGUAGE(1, "Русский язык", R.drawable.russian_language),
    LITERATURE(2, "Литература", R.drawable.literature),
    ALGEBRA(3, "Алгебра", R.drawable.algebra),
    GEOMETRY(4, "Геометрия", R.drawable.geometry),
    HISTORY(5, "История", R.drawable.history),
    GEOGRAPHY(6, "География", R.drawable.geography),
    PHYSICS(7, "Физика",R.drawable.physics),
    CHEMISTRY(8, "Химия",R.drawable.chemistry),
    BIOLOGY(9, "Биология", R.drawable.biology),
    ENGLISH(10, "Английский язык", R.drawable.english_language),
    INFORMATICS(11, "Информатика", R.drawable.informatics),
    PHYSICAL_EDUCATION(12, "Физкультура", R.drawable.physical_education),
    SOCIAL_STUDIES(13, "Обществознание", R.drawable.social_studies);

    companion object {
        fun getById(id: Int?): SubjectEnum? = entries.find { it.id == id }
    }
}
