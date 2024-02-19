package teka.android.chamaaapp.features.feature_auth.presentation.login

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import teka.android.chamaaapp.domain.models.TextFieldState
import teka.android.chamaaapp.util.UiEvents
import teka.android.chamaaapp.features.feature_auth.AuthViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import teka.android.chamaaapp.R
import teka.android.chamaaapp.navigation.Screen
import teka.android.chamaaapp.navigation.To_MAIN_GRAPH_ROUTE
import teka.android.chamaaapp.ui.theme.GrayColor
import teka.android.chamaaapp.ui.theme.MainWhiteColor
import teka.android.chamaaapp.ui.theme.YellowMain
import teka.android.chamaaapp.ui.theme.primaryDark
import teka.android.chamaaapp.ui.theme.primaryGray
import teka.android.chamaaapp.ui.theme.quicksand

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navigator: NavHostController,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val authViewModel: AuthViewModel = hiltViewModel()

    val usernameState = authViewModel.usernameState.value
    val passwordState = authViewModel.passwordState.value
    val rememberMeState = authViewModel.rememberMeState.value

    val loginState = authViewModel.loginState.value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

//    val loginResult by authViewModel.loginResult.observeAsState()


    val keyboardController = LocalSoftwareKeyboardController.current


    LaunchedEffect(key1 = true) {
        authViewModel.loginEventFlow.collectLatest { event ->
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
                            message = "Successfully Logged In",
//                            actionLabel = "Click me",
                            duration = SnackbarDuration.Long
                        )
                    }
                    delay(1000)

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
            Column(
                Modifier.padding(16.dp), verticalArrangement = Arrangement.Top
            ) {
                Text(text = "Hello Again!", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = "Login to your account ",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light
                )
            }
        },
//        scaffoldState = scaffoldState
    ) {
        LoginScreenContent(
            usernameState = usernameState,
            passwordState = passwordState,
            rememberMeState = rememberMeState,
            loginState = loginState,
            onUserNameTextChange = {
                authViewModel.setUsername(it)
            },
            onPasswordTextChange = {
                authViewModel.setPassword(it)
            },
            onRememberMeClicked = {
                authViewModel.setRememberMe(it)
            },
            onClickForgotPassword = {
                navigator.navigate(Screen.ForgotPasswordScreen.route)
            },
            onClickDontHaveAccount = {
                navigator.popBackStack()
                navigator.navigate(Screen.RegisterScreen.route)
            },
            onClickSignIn = {
                keyboardController?.hide()
                authViewModel.login(username = usernameState.text, password = passwordState.text)
            }
        )
    }
}

@Composable
private fun LoginScreenContent(
    usernameState: TextFieldState,
    passwordState: TextFieldState,
    rememberMeState: Boolean,
    loginState: LoginState,
    onUserNameTextChange: (String) -> Unit,
    onPasswordTextChange: (String) -> Unit,
    onRememberMeClicked: (Boolean) -> Unit,
    onClickForgotPassword: () -> Unit,
    onClickDontHaveAccount: () -> Unit,
    onClickSignIn: () -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
    ) {
        item {
            Spacer(modifier = Modifier.height(114.dp))
        }
        item {
            Column {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = usernameState.text,
                    onValueChange = {
                        onUserNameTextChange(it)
                    },
                    label = {
                        Text(text = "Phone/Email")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                    ),
                    maxLines = 1,
                    singleLine = true,
                    isError = usernameState.error != null,
                    colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = primaryGray,
                        focusedTextColor = primaryDark,
                        unfocusedTextColor = GrayColor,
                        unfocusedLabelColor = primaryGray,
                        focusedLabelColor = primaryDark,
                        cursorColor = Color.Black
                    ),
                )
                if (usernameState.error != "") {
                    Text(
                        text = usernameState.error ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))

            Column {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = passwordState.text,
                    onValueChange = {
                        onPasswordTextChange(it)
                    },
                    label = {
                        Text(text = "Password")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                    ),
                    maxLines = 1,
                    singleLine = true,
                    isError = passwordState.error != null,
                    colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
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
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onClickSignIn,
                shape = RoundedCornerShape(25.dp),
                enabled = !loginState.isLoading
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    text = "Log In",
                    textAlign = TextAlign.Center,
                    color = MainWhiteColor,
                    fontFamily = quicksand
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onClickForgotPassword) {
                    Text(text = "Forgot password?")
                }
            }
        }

        item {
            TextButton(
                onClick = onClickDontHaveAccount,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Don't have an account?")
                        append(" ")
                        withStyle(
                            style = SpanStyle(color = YellowMain, fontWeight = FontWeight.Bold)
                        ) {
                            append("Sign Up")
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
                if (loginState.isLoading) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}