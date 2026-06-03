package com.example.classschedule.Presentation.entrance.register

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.classschedule.Presentation.navigation.Screen
import com.example.classschedule.Presentation.ui.theme.link
import com.example.classschedule.Presentation.ui.utils.StyleButton
import com.example.classschedule.Presentation.ui.utils.StyleOutlinedTextField
import com.example.classschedule.R

@Composable
fun RegisterScreen(
    navigate: (Screen) -> Unit

) {
    val viewModel: RegisterViewModel = hiltViewModel<RegisterViewModel, RegisterViewModel.Factory> {
        it.create(navigate)
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    RegisterView(
        viewModel = viewModel,
        navigation = navigate,
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun RegisterView(
    viewModel: RegisterViewModel,
    navigation: (Screen) -> Unit,
    state: RegisterState,
    onEvent: (RegisterEvent) -> Unit
) {
    val wasFocusedEmailTextField = remember { mutableStateOf(false) }
    val wasFocusedPasswordTextField = remember { mutableStateOf(false) }
    val context = LocalContext.current



    LaunchedEffect(viewModel.errorEvents) {
        viewModel.errorEvents.collect { message ->
            Toast.makeText(context, message.asString(context), Toast.LENGTH_LONG).show()

        }
    }



    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                modifier = Modifier
                    .padding(top = 150.dp)
                    .size(160.dp),
                imageVector = Icons.Default.PersonOutline,
                contentDescription = null,

                )
            Text(
                text = stringResource(R.string.RegisterPage),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.padding(25.dp))
            StyleOutlinedTextField(
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ), resourceStringId = R.string.Name,
                value = state.name
            ) { onEvent(RegisterEvent.NameEditEvent(it)) }

            StyleOutlinedTextField(
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                resourceStringId = R.string.Surname,
                value = state.surname

            ) { onEvent(RegisterEvent.SurnameEditEvent(it)) }

            StyleOutlinedTextField(
                animateErrorText = stringResource(R.string.WrongEmail),
                isError = !state.isValidEmail && !wasFocusedEmailTextField.value,
                wasFocused = wasFocusedEmailTextField,
                resourceStringId = R.string.Email,
                value = state.email,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                )
            ) { onEvent(RegisterEvent.EmailEditEvent(it)) }




            StyleOutlinedTextField(
                animateErrorText = stringResource(R.string.ShortPassword),
                showPassword = true,
                visualTransformation = PasswordVisualTransformation(),
                isError = !state.isValidPassword && !wasFocusedPasswordTextField.value,
                wasFocused = wasFocusedPasswordTextField,
                resourceStringId = R.string.Password,
                value = state.password,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
            ) { onEvent(RegisterEvent.PasswordEditEvent(it)) }

            StyleButton(
                enabled = state.canNavigateToMainScreen,
                onClick = { onEvent(RegisterEvent.JoinButtonEvent) },
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(56.dp)
            ) {
                Text(stringResource(R.string.Register))
            }
            Text(
                text = stringResource(R.string.AlreadyHaveAcc),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.link,
                modifier = Modifier
                    .clickable(
                        onClick = { navigation(Screen.DefaultEntrance) }
                    )

            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun ShowRegisterView() {
    RegisterView(
        navigation = {},
        viewModel = viewModel(),
        state = RegisterState(),
        onEvent = { }
    )
}