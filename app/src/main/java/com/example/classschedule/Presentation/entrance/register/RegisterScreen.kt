package com.example.classschedule.Presentation.entrance.register

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.classschedule.Presentation.navigation.Screen
import com.example.classschedule.Presentation.ui.utils.StyleButton
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
    var wasFocusedEmailTextField by remember { mutableStateOf(false) }
    var wasFocusedPasswordTextField by remember { mutableStateOf(false) }
    val context = LocalContext.current



    LaunchedEffect(viewModel.errorEvents) {
        viewModel.errorEvents.collect { message ->
            Toast.makeText(context, message.asString(context), Toast.LENGTH_LONG).show()

        }
    }









    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .fillMaxSize()
    ) {
        Icon(
            modifier = Modifier
                .padding(top = 70.dp)
                .size(200.dp),
            imageVector = Icons.Default.AccountBox,
            contentDescription = null,

            )
        Text(
            text = stringResource(R.string.RegisterPage),
            fontSize = 40.sp
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.70f)
                .padding(top = 30.dp),
            value = state.name,
            onValueChange = { onEvent(RegisterEvent.NameEditEvent(it)) },
            placeholder = {
                Text(text = stringResource(R.string.Name))
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),


            )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.70f),
            value = state.surname,
            onValueChange = { onEvent(RegisterEvent.SurnameEditEvent(it)) },
            placeholder = {
                Text(text = stringResource(R.string.Surname))
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),

            )


        Column(
            modifier = Modifier
                .animateContentSize()
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.70f)
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            wasFocusedEmailTextField = true
                        }
                        if (!focusState.isFocused && wasFocusedEmailTextField) {
                            onEvent(RegisterEvent.EmailFocusLostEvent)
                        }
                    },
                singleLine = true,
                value = state.email,
                onValueChange = { onEvent(RegisterEvent.EmailEditEvent(it)) },
                placeholder = {
                    Text(text = stringResource(R.string.Email))
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),
                isError = !state.isValidEmail,

                )
            AnimatedVisibility(
                visible = !state.isValidEmail,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Text(
                    text = stringResource(R.string.WrongEmail),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }

        Column(
            modifier = Modifier
                .animateContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.70f)
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            wasFocusedPasswordTextField = true
                        }

                    },
                value = state.password,
                onValueChange = { onEvent(RegisterEvent.PasswordEditEvent(it)) },
                placeholder = {
                    Text(text = stringResource(R.string.Password))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),

                visualTransformation = PasswordVisualTransformation()


            )
            AnimatedVisibility(
                visible = state.password.length < 6 && wasFocusedPasswordTextField,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut(),
            )
            {
                Text(
                    text = stringResource(R.string.ShortPassword),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
        StyleButton(
            enabled = state.canNavigateToMainScreen,
            onClick = { onEvent(RegisterEvent.JoinButtonEvent) },
            modifier = Modifier
                .width(280.dp)
                .height(60.dp)
        ) {
            Text(stringResource(R.string.Register))
        }
        Text(
            text = stringResource(R.string.AlreadyHaveAcc),
            modifier = Modifier
                .clickable(
                    onClick = { navigation(Screen.DefaultEntrance) }
                )

        )
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