package com.example.classschedule.Domain.constants

enum class SubjectEnum(val id: Int, val title: String) {
    RUSSIAN_LANGUAGE(1, "Русский язык"),
    LITERATURE(2, "Литература"),
    ALGEBRA(3, "Алгебра"),
    GEOMETRY(4, "Геометрия"),
    HISTORY(5, "История"),
    GEOGRAPHY(6, "География"),
    PHYSICS(7, "Физика"),
    CHEMISTRY(8, "Химия"),
    BIOLOGY(9, "Биология"),
    ENGLISH(10, "Английский язык"),
    INFORMATICS(11, "Информатика"),
    PHYSICAL_EDUCATION(12, "Физкультура"),
    SOCIAL_STUDIES(13, "Обществознание");

    companion object {
        fun getById(id: Int?): SubjectEnum? = entries.find { it.id == id }
    }
}
