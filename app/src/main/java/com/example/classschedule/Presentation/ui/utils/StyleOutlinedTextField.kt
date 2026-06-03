package com.example.classschedule.Presentation.ui.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PanoramaFishEye
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.classschedule.Presentation.ui.theme.darkError
import com.example.classschedule.R

@Composable
fun StyleOutlinedTextField(
    animateErrorText: String = "",
    showPassword: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError: Boolean = false,
    wasFocused: MutableState<Boolean>? = null,
    resourceStringId: Int,
    value: String,
    keyboardOptions: KeyboardOptions,
    onValueChanged: (String) -> Unit,
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(modifier = Modifier.animateContentSize()) {
        OutlinedTextField(

            modifier = Modifier
                .fillMaxWidth(0.85f)
                .clip(RoundedCornerShape(12.dp))
                .onFocusChanged { focusState ->
                    wasFocused?.let { wasFocused ->
                        wasFocused.value = focusState.isFocused
                    }

                },
            colors = TextFieldDefaults.colors(
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorContainerColor = MaterialTheme.colorScheme.error,
                cursorColor = Color(0xFF007AFF),
                errorCursorColor = Color(0xFF007AFF),
                focusedTextColor = Color.Black,
                errorIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            value = value,
            onValueChange = { onValueChanged(it) },
            placeholder = {
                Text(
                    color = MaterialTheme.colorScheme.onSurface,
                    text = stringResource(resourceStringId),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            },
            keyboardOptions = keyboardOptions,
            isError = isError,
            trailingIcon = {
                if (showPassword) {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            imageVector = if (isPasswordVisible) Icons.Default.PanoramaFishEye else Icons.Default.RemoveRedEye,
                            contentDescription = null,
                            tint = Color(0xFF8E8E93)
                        )
                    }
                }
            },
            visualTransformation = if (showPassword) {
                if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
            } else {
                visualTransformation
            },
        )
        AnimatedVisibility(
            visible = isError,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut(),

            ) {
            Text(
                text = animateErrorText,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.darkError
            )
        }
    }
}