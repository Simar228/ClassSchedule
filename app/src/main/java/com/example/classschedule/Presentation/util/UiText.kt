package com.example.classschedule.Presentation.util

import android.content.Context

sealed class UiText {
    data class Resource(val resId: Int) : UiText()
    data class DynamicString(val value: String) : UiText()
    fun asString(context: Context): String {
        return when (this) {
            is Resource -> context.getString(resId)
            is DynamicString -> value
        }
    }
}