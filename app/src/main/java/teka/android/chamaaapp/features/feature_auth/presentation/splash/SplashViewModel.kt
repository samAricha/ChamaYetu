package teka.android.chamaaapp.features.feature_auth.presentation.splash

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import teka.android.chamaaapp.features.feature_auth.domain.use_case.AutoLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import teka.android.chamaaapp.features.feature_auth.UserState
import teka.android.chamaaapp.features.feature_auth.repository.DataStoreRepository
import teka.android.chamaaapp.navigation.AUTH_GRAPH_ROUTE
import teka.android.chamaaapp.navigation.To_MAIN_GRAPH_ROUTE
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: DataStoreRepository
) : ViewModel() {

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _startDestination = mutableStateOf<String?>(null)
    val startDestination: MutableState<String?> = _startDestination



    init {
        viewModelScope.launch {
            repository.readLoggedInState().collect { completed ->
                if (completed) {
                    _startDestination.value = To_MAIN_GRAPH_ROUTE
                } else {
                    _startDestination.value = AUTH_GRAPH_ROUTE
                }
            }
            _isLoading.value = false
        }
    }

    fun getLoggedInState(){
        viewModelScope.launch {
            repository.readLoggedInState().collect { completed ->
                if (completed) {
                    _startDestination.value = To_MAIN_GRAPH_ROUTE
                } else {
                    _startDestination.value = AUTH_GRAPH_ROUTE
                }
            }
            _isLoading.value = false

        }
    }

}