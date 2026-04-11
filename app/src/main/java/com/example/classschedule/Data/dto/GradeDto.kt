package com.example.classschedule.Data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class GradeDto (
    @SerialName("value")val grade: Int,

)