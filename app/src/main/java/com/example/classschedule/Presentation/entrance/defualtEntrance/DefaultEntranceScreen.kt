package com.example.classschedule.Presentation.entrance.defualtEntrance

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
fun DefaultEntranceScreen(
    navigation: (Screen) -> Unit,

    ) {
    val viewModel: DefaultEntranceViewModel =
        hiltViewModel<DefaultEntranceViewModel, DefaultEntranceViewModel.Factory> {
            it.create(navigation)
        }
    val state by viewModel.state.collectAsStateWithLifecycle()

    DefaultEntranceView(
        navigation = navigation, state = state, onEvent = viewModel::onEvent, viewModel = viewModel
    )
}

@Composable
private fun DefaultEntranceView(
    navigation: (Screen) -> Unit,
    state: DefaultEntranceState,
    onEvent: (DefualtEntranceEvent) -> Unit,
    viewModel: DefaultEntranceViewModel
) {


    val context = LocalContext.current
    val wasFocusedEmail = remember { mutableStateOf(false) }
    val wasFocusedPassword = remember { mutableStateOf(false) }

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
                text = stringResource(R.string.LoginPage),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(Modifier.padding(100.dp))
            StyleOutlinedTextField(
                wasFocused = wasFocusedEmail,
                resourceStringId = R.string.Email,
                value = state.email,
                isError = !state.isValidEmail && !wasFocusedEmail.value,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next, keyboardType = KeyboardType.Email
                ),
                animateErrorText = stringResource(R.string.WrongEmail),
            ) { newEmail ->
                onEvent(DefualtEntranceEvent.EmailEditEvent(newEmail))
            }



            StyleOutlinedTextField(
                animateErrorText = stringResource(R.string.WrongPassword),
                showPassword = true,
                visualTransformation = PasswordVisualTransformation(),
                wasFocused = wasFocusedPassword,
                resourceStringId = R.string.Password,
                value = state.password,
                isError = !state.validPassword && !wasFocusedPassword.value,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
                ),
            ) { password ->
                onEvent(DefualtEntranceEvent.PasswordEditEvent(password))
            }


            StyleButton(
                enabled = state.canNavigateToMainScreen,
                onClick = { onEvent(DefualtEntranceEvent.LoginButtonEvent) },
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(56.dp)
            ) {
                Text(
                    text = stringResource(R.string.Login),
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Text(
                text = stringResource(R.string.HaventAcc),
                color = MaterialTheme.colorScheme.link,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.clickable(
                    onClick = { navigation(Screen.Register) })

            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun ShowDefaultEntranceView() {
    DefaultEntranceView({}, DefaultEntranceState(), {}, viewModel())
}