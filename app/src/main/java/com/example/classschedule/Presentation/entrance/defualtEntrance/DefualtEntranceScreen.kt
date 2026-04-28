package com.example.classschedule.Presentation.entrance.defualtEntrance

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
fun DefualtEntranceScreen(
    navigation: (Screen) -> Unit,

    ) {
    val viewModel: DefaultEntranceViewModel =
        hiltViewModel<DefaultEntranceViewModel, DefaultEntranceViewModel.Factory> {
            it.create(navigation)
        }
    val state by viewModel.state.collectAsStateWithLifecycle()

    DefaultEntranceView(
        navigation = navigation,
        state = state,
        onEvent = viewModel::onEvent,
        viewModel = viewModel
    )
}

@Composable
private fun DefaultEntranceView(
    navigation: (Screen) -> Unit,
    state: DefualtEntranceState,
    onEvent: (DefualtEntranceEvent) -> Unit,
    viewModel: DefaultEntranceViewModel
) {


    val context = LocalContext.current
    var wasFocusedEmail by remember { mutableStateOf(false) }
    var wasFocusedPassword by remember { mutableStateOf(false) }

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
                .padding(top = 100.dp)
                .size(200.dp),
            imageVector = Icons.Default.AccountBox,
            contentDescription = null,

            )
        Text(
            text = stringResource(R.string.LoginPage),
            fontSize = 40.sp
        )
        Column(
            modifier = Modifier.animateContentSize()
        ) {
            OutlinedTextField(

                modifier = Modifier
                    .fillMaxWidth(0.70f)
                    .padding(top = 80.dp)
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            wasFocusedEmail = true
                        }
                        if (!focusState.isFocused && wasFocusedEmail) {
                            onEvent(DefualtEntranceEvent.EmailFocusLostEvent)
                        }
                    },
                singleLine = true,
                value = state.email,
                onValueChange = { onEvent(DefualtEntranceEvent.EmailEditEvent(it)) },
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
                modifier = Modifier,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut(),

                ) {
                Text(
                    text = stringResource(R.string.WrongEmail),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
        Column() {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.70f)
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            wasFocusedPassword = true
                        }
                    },
                value = state.password,
                onValueChange = { onEvent(DefualtEntranceEvent.PasswordEditEvent(it)) },
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
                visible = state.password.length <= 5 && wasFocusedPassword,
                modifier = Modifier,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut(),

                ) {
                Text(
                    text = stringResource(R.string.WrongPassword),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
        StyleButton(
            enabled = state.canNavigateToMainScreen,
            onClick = { onEvent(DefualtEntranceEvent.LoginButtonEvent) },
            modifier = Modifier
                .width(280.dp)
                .height(60.dp)
        ) {
            Text(stringResource(R.string.Login))
        }
        Text(
            text = stringResource(R.string.HaventAcc),
            modifier = Modifier
                .clickable(
                    onClick = { navigation(Screen.Register) }
                )

        )
    }
}


@Composable
@Preview(showBackground = true)
fun ShowDefaultEntranceView() {
    DefaultEntranceView({}, DefualtEntranceState(), {}, viewModel())
}