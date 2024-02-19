package teka.android.chamaaapp.features.feature_auth

import android.annotation.SuppressLint
import androidx.compose.runtime.State
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import teka.android.chamaaapp.features.feature_auth.presentation.login.LoginState
import teka.android.chamaaapp.features.feature_auth.repository.DataStoreRepository
import teka.android.chamaaapp.features.feature_auth.util.AuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import teka.android.chamaaapp.features.feature_auth.util.RegistrationResult
import timber.log.Timber
import javax.inject.Inject
import teka.android.chamaaapp.domain.models.TextFieldState
import teka.android.chamaaapp.features.feature_auth.domain.model.LoginResult
import teka.android.chamaaapp.features.feature_auth.domain.model.RegistrationValidationResultModel
import teka.android.chamaaapp.features.feature_auth.domain.use_case.LoginDetailsValidationUseCase
import teka.android.chamaaapp.features.feature_auth.domain.use_case.RegistrationDetailsValidationUseCase
import teka.android.chamaaapp.features.feature_auth.util.LoginResultModel
import teka.android.chamaaapp.navigation.Screen
import teka.android.chamaaapp.navigation.To_MAIN_GRAPH_ROUTE
import teka.android.chamaaapp.util.ResultResource
import teka.android.chamaaapp.util.UiEvents


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authManager: AuthManager,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _usernameState = mutableStateOf(TextFieldState(text = ""))
    val usernameState: State<TextFieldState> = _usernameState
    fun setUsername(value: String) {
        _usernameState.value = usernameState.value.copy(text = value)
    }

    private val _passwordState = mutableStateOf(TextFieldState(text = ""))
    val passwordState: State<TextFieldState> = _passwordState
    fun setPassword(value: String) {
        _passwordState.value = _passwordState.value.copy(text = value)
    }

    private val _rememberMeState = mutableStateOf(false)
    val rememberMeState: State<Boolean> = _rememberMeState
    fun setRememberMe(value: Boolean) {
        _rememberMeState.value = value
    }



    var isLoggedInState: Flow<Boolean> = dataStoreRepository.readLoggedInState()

    private val _registrationState = mutableStateOf(LoginState())
    val registrationState: State<LoginState> = _registrationState

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    private var _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated

    private val _isRegistered = MutableStateFlow(false)
    val isRegistered: StateFlow<Boolean> = _isRegistered

    private val _loginEventFlow = MutableSharedFlow<UiEvents>()
    val loginEventFlow = _loginEventFlow.asSharedFlow()
    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = loginState.value.copy(isLoading = true)
            val validationResult = LoginDetailsValidationUseCase()(username = username, password =  password, false)

            if (validationResult.result is ResultResource.Success) {
                // Validation passed, proceed with the repository call
                performLogin(username, password, false)
            } else {
                // Validation failed, update the result
                updateLoginResult(validationResult)
                _loginState.value = _loginState.value.copy(isLoading = false)
            }
            _loginState.value = loginState.value.copy(isLoading = false)

        }
    }


//    val loginResult = MutableLiveData<LoginResult>()
    private fun updateLoginResult(result: LoginResult) {
        _usernameState.value = usernameState.value.copy(error = result.usernameError)
        _passwordState.value = passwordState.value.copy(error = result.passwordError)
//        loginResult.value = result
    }

    private suspend fun performLogin(username: String, password: String, rememberMe: Boolean) {
        try {
            // Make the actual repository call
            when (val result: LoginResultModel = authManager.login(username, password)) {
                is LoginResultModel.Success -> {
                    dataStoreRepository.saveLoggedInState(isLoggedIn = true)
                    _loginEventFlow.emit(UiEvents.NavigateEvent(To_MAIN_GRAPH_ROUTE))
                }
                is LoginResultModel.Failure -> {
                    val errorMessage = result.errorMessage?.uppercase()
                    _loginEventFlow.emit(UiEvents.SnackbarEvent("Login failed::. $errorMessage!!"))
                }
            }
        } catch (e: Exception) {
            // Handle errors or exceptions from the repository call
            _loginEventFlow.emit(UiEvents.SnackbarEvent("Login failed:: ${e.localizedMessage}"))
        } finally {
            _loginState.value = _loginState.value.copy(isLoading = false)
        }
    }




    private val _registrationEventFlow = MutableSharedFlow<UiEvents>()
    val registrationEventFlow = _registrationEventFlow.asSharedFlow()
    private val _registrationPhoneState = mutableStateOf(TextFieldState(text = ""))
    val registrationPhoneState: State<TextFieldState> = _registrationPhoneState
    private val _registrationEmailState = mutableStateOf(TextFieldState(text = ""))
    val registrationEmailState: State<TextFieldState> = _registrationEmailState
    private val _registrationPasswordState = mutableStateOf(TextFieldState(text = ""))
    val registrationPasswordState: State<TextFieldState> = _registrationPasswordState
    private val _registrationPasswordConfirmState = mutableStateOf(TextFieldState(text = ""))
    val registrationPasswordConfirmState: State<TextFieldState> = _registrationPasswordConfirmState


    fun setRegistrationPhone(value: String) {
        _registrationPhoneState.value = registrationPhoneState.value.copy(text = value)
    }

    fun setRegistrationEmail(value: String) {
        _registrationEmailState.value = registrationEmailState.value.copy(text = value)
    }

    fun setRegistrationPassword(value: String) {
        _registrationPasswordState.value = registrationPasswordState.value.copy(text = value)
    }

    fun setRegistrationConfirmPassword(value: String) {
        _registrationPasswordConfirmState.value = registrationPasswordConfirmState.value.copy(text = value)
    }

    fun register(
        phone: String,
        email: String,
        password: String,
        passwordConfirmation: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _registrationState.value = loginState.value.copy(isLoading = true)

            val last9Digits = phone.takeLast(9)
            val formattedPhoneNumber = "254$last9Digits"

            val validationResult = RegistrationDetailsValidationUseCase()(
                phone = phone,
                email = email,
                password = password,
                passwordConfirmation = passwordConfirmation,
                false
            )

            if (validationResult.result is ResultResource.Success) {
                performRegistration(phone = formattedPhoneNumber,email = email, password = password,passwordConfirmation = passwordConfirmation, false)
            } else {
                updateRegistrationResult(validationResult)
                _registrationState.value = _loginState.value.copy(isLoading = false)
            }

            _registrationState.value = loginState.value.copy(isLoading = false)
        }
    }

    private suspend fun performRegistration(
        phone: String,
        email: String,
        password: String,
        passwordConfirmation: String,
        rememberMe: Boolean
    ) {

        val last9Digits = phone.takeLast(9)
        val formattedPhoneNumber = "254$last9Digits"

        try {
            val result: RegistrationResult<Boolean> = authManager.register(
                phone = formattedPhoneNumber,
                email = email,
                password = password,
                passwordConfirmation = passwordConfirmation
            )

            _isRegistered.value = when (result) {
                is RegistrationResult.Success -> {
                    _isRegistered.value = result.data
                    _registrationState.value = registrationState.value.copy(
                        isLoading = false
                    )
//                    if (result.data)dataStoreRepository.saveLoggedInState(isLoggedIn = result.data)
                    _registrationEventFlow.emit(UiEvents.NavigateEvent(Screen.LoginScreen.route))
                    result.data
                }
                is RegistrationResult.Failure -> {
                    Timber.e(result.exception, "Registration failed.")
                    val errorMessage = result.errorMessage?.uppercase()

                    _registrationEventFlow.emit(UiEvents.SnackbarEvent("Registration failed!!::.${errorMessage}"))
                    false
                }

                else -> {
                    false
                }
            }
        } catch (e: Exception) {
            // Handle errors or exceptions from the repository call
            _registrationEventFlow.emit(UiEvents.SnackbarEvent("Registration failed: ${e.localizedMessage}"))
        } finally {
            _registrationState.value = registrationState.value.copy(isLoading = false)
        }
    }

    private fun updateRegistrationResult(result: RegistrationValidationResultModel) {
        _registrationPhoneState.value = registrationPhoneState.value.copy(error = result.phoneError)
        _registrationEmailState.value = registrationEmailState.value.copy(error = result.emailError)
        _registrationPasswordState.value = registrationPasswordState.value.copy(error = result.passwordError)
        _registrationPasswordConfirmState.value = registrationPasswordConfirmState.value.copy(error = result.passwordError)
    }


    fun logout() {
        viewModelScope.launch {
            authManager.clearAuthToken()
            dataStoreRepository.saveLoggedInState(false)
        }
    }




}


@SuppressLint("CompositionLocalNaming")
val UserState = compositionLocalOf<AuthViewModel> { error("User State Context Not Found!") }