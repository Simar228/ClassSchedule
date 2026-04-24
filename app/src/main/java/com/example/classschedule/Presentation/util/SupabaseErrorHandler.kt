package com.example.classschedule.Presentation.util

import android.util.Log
import com.example.classschedule.Data.util.NetworkMonitor
import com.example.classschedule.R
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.exceptions.HttpRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import java.net.ConnectException
import java.net.UnknownHostException

fun <T> Result<T>.suppabaseErrorHandler(onFailure: () -> Unit = {}, tag : String = "Supabase", onSuccess : () -> Unit = {}) : UiText {


    this.onFailure { exception ->
        Log.e(tag, exception.toString())
        onFailure()
        when (exception) {

            is ConnectException,
            is UnknownHostException,
            is HttpRequestTimeoutException,
            is HttpRequestException-> {
                NetworkMonitor.statusInternet(false)
                return UiText.Resource(R.string.invalid_internet)
            }


            is AuthRestException -> {
                val uiText = when (exception.errorCode.toString()) {
                    "user_already_exists" -> UiText.Resource(R.string.user_already_exists)
                    "over_email_send_rate_limit" -> UiText.Resource(R.string.over_email_send_rate_limit)
                    "weak_password" -> UiText.Resource(R.string.weak_password)
                    "invalid_credentials" -> UiText.Resource(R.string.invalid_credentials)
                    else -> {
                        UiText.DynamicString(exception.message ?: "Unknown error")
                    }
                }
                return uiText
            }

            else -> {
                return UiText.DynamicString("Unknown error: ${exception.localizedMessage}")
            }

        }
    }
    this.onSuccess {
        onSuccess()

    }


    return UiText.DynamicString("")
}