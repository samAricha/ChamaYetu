package teka.android.chamaaapp.features.feature_auth.presentation.register

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import teka.android.chamaaapp.domain.models.TextFieldState
import teka.android.chamaaapp.features.feature_auth.AuthViewModel
import teka.android.chamaaapp.features.feature_auth.presentation.login.LoginState
import teka.android.chamaaapp.navigation.Screen
import teka.android.chamaaapp.ui.theme.GrayColor
import teka.android.chamaaapp.ui.theme.MainWhiteColor
import teka.android.chamaaapp.ui.theme.YellowMain
import teka.android.chamaaapp.ui.theme.primaryDark
import teka.android.chamaaapp.ui.theme.primaryGray
import teka.android.chamaaapp.ui.theme.quicksand
import teka.android.chamaaapp.util.UiEvents

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegisterScreen(
    navigator: NavHostController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val context = LocalContext.current

    val phoneState = authViewModel.registrationPhoneState.value
    val emailState = authViewModel.registrationEmailState.value
    val passwordState = authViewModel.registrationPasswordState.value
    val passwordConfirmationState = authViewModel.registrationPasswordConfirmState.value

    val registrationState = authViewModel.registrationState.value
    val isRegistered by authViewModel.isRegistered.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val keyboardController = LocalSoftwareKeyboardController.current


    LaunchedEffect(key1 = true) {
        authViewModel.registrationEventFlow.collectLatest { event ->
            when (event) {
                is UiEvents.SnackbarEvent -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = event.message,
//                            actionLabel = "Click me",
                            duration = SnackbarDuration.Long
                        )
                    }
                }
                is UiEvents.NavigateEvent -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Successfully Registered",
//                            actionLabel = "Click me",
                            duration = SnackbarDuration.Long
                        )
                        delay(1000)
                        navigator.navigate(event.route)
                    }
                    navigator.navigate(
                        event.route
                    )
                }
            }
        }
    }




    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.Top) {
                Text(text = "Welcome", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = "Create account",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }
    ) {
        RegisterScreenContent(
            phoneState = phoneState,
            emailState= emailState,
            passwordState = passwordState,
            registrationState = registrationState,
            onPhoneTextChange = {
                authViewModel.setRegistrationPhone(it)
            },
            onEmailTextChange = {
                authViewModel.setRegistrationEmail(it)
            },
            onPasswordTextChange = {
                authViewModel.setRegistrationPassword(it)
            },
            onPasswordConfirmationTextChange = {
                authViewModel.setRegistrationConfirmPassword(it)
            },
            onClickSignUp = {
                keyboardController?.hide()
                authViewModel.register(
                    phone = phoneState.text,
                    email = emailState.text,
                    password = passwordState.text,
                    passwordConfirmation = passwordState.text
                )
            },
            onClickHaveAccount = {
                keyboardController?.hide()
                navigator.navigate(Screen.LoginScreen.route)
            }
        )
    }
}

@Composable
private fun RegisterScreenContent(
    phoneState: TextFieldState,
    emailState: TextFieldState,
    passwordState: TextFieldState,
    registrationState: LoginState,
    onPhoneTextChange: (String) -> Unit,
    onEmailTextChange: (String) -> Unit,
    onPasswordTextChange: (String) -> Unit,
    onPasswordConfirmationTextChange: (String) -> Unit,
    onClickSignUp: () -> Unit,
    onClickHaveAccount: () -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(64.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = phoneState.text,
                onValueChange = {
                    onPhoneTextChange(it)
                },
                label = {
                    Text(text = "Phone")
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    autoCorrect = true,
                    keyboardType = KeyboardType.Phone,
                ),
                maxLines = 1,
                singleLine = true,
                isError = phoneState.error != null,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = primaryGray,
                    focusedTextColor = primaryDark,
                    unfocusedTextColor = GrayColor,
                    unfocusedLabelColor = primaryGray,
                    focusedLabelColor = primaryDark,
                    cursorColor = Color.Black
                )
            )
            if (phoneState.error != "") {
                Text(
                    text = phoneState.error ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }

        }
        item {
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = emailState.text,
                onValueChange = {
                    onEmailTextChange(it)
                },
                label = {
                    Text(text = "Email")
                },
                keyboardOptions = KeyboardOptions(
                    autoCorrect = true,
                    keyboardType = KeyboardType.Email,
                ),
                maxLines = 1,
                singleLine = true,
                isError = emailState.error != null,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = primaryGray,
                    focusedTextColor = primaryDark,
                    unfocusedTextColor = GrayColor,
                    unfocusedLabelColor = primaryGray,
                    focusedLabelColor = primaryDark,
                    cursorColor = Color.Black
                )
            )
            if (emailState.error != "") {
                Text(
                    text = emailState.error ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = passwordState.text,
                onValueChange = {
                    onPasswordTextChange(it)
                    onPasswordConfirmationTextChange(it)
                },
                label = {
                    Text(text = "Password")
                },
                keyboardOptions = KeyboardOptions(
                    autoCorrect = true,
                    keyboardType = KeyboardType.Password,
                ),
                maxLines = 1,
                singleLine = true,
                isError = passwordState.error != null,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = primaryGray,
                    focusedTextColor = primaryDark,
                    unfocusedTextColor = GrayColor,
                    unfocusedLabelColor = primaryGray,
                    focusedLabelColor = primaryDark,
                    cursorColor = Color.Black
                )
            )
            if (passwordState.error != "") {
                Text(
                    text = passwordState.error ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(32.dp))

            val context = LocalContext.current

            Button(
                enabled = !registrationState.isLoading,
                onClick = onClickSignUp,
                shape = RoundedCornerShape(8)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    text = "Sign Up",
                    textAlign = TextAlign.Center,
                    color = MainWhiteColor
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(24.dp))

            TextButton(
                onClick = onClickHaveAccount,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Already have an account?")
                        append(" ")
                        withStyle(
                            style = SpanStyle(color = YellowMain, fontWeight = FontWeight.Bold)
                        ) {
                            append("Sign In")
                        }
                    },
                    fontFamily = quicksand,
                    textAlign = TextAlign.Center
                )
            }

        }

        item {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (registrationState.isLoading) {
                    CircularProgressIndicator()
                }
            }
        }

    }
}