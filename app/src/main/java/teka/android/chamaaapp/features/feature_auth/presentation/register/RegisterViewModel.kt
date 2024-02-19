package teka.android.chamaaapp.features.feature_auth.presentation.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import teka.android.chamaaapp.domain.models.TextFieldState
import teka.android.chamaaapp.util.UiEvents
import teka.android.chamaaapp.features.feature_auth.domain.use_case.LoginUseCase
import teka.android.chamaaapp.features.feature_auth.presentation.login.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

//    private val _usernameState = mutableStateOf(TextFieldState(text = "johnd"))
    private val _phoneState = mutableStateOf(TextFieldState(text = ""))
    val phoneState: State<TextFieldState> = _phoneState
    fun setPhone(value: String) {
        _phoneState.value = phoneState.value.copy(text = value)
    }

    private val _emailState = mutableStateOf(TextFieldState(text = ""))
    val emailState: State<TextFieldState> = _emailState
    fun setEmail(value: String) {
        _emailState.value = emailState.value.copy(text = value)
    }

//    private val _passwordState = mutableStateOf(TextFieldState(text = "m38rmF$"))
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

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()
}