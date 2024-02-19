package teka.android.chamaaapp.presentation.addMember

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import teka.android.chamaaapp.data.local.repository.DbRepository
import teka.android.chamaaapp.data.local.room.entities.MemberEntity
import teka.android.chamaaapp.domain.models.Task
import teka.android.chamaaapp.features.feature_auth.presentation.login.LoginState
import teka.android.chamaaapp.util.UiEvents
import teka.android.chamaaapp.util.today
import javax.inject.Inject

@HiltViewModel
class AddMemberViewModel @Inject constructor(
    context: Context,
    private val repository: DbRepository
) : ViewModel() {
    private val _eventsFlow = Channel<UiEvents>(Channel.UNLIMITED)
    val eventsFlow = _eventsFlow.receiveAsFlow()


    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState


    private val _memberName = MutableStateFlow("")
    val memberName = _memberName.asStateFlow()
    fun setMemberName(name: String) {
        _memberName.value = name
    }

    private val _memberPhone = MutableStateFlow("")
    val memberPhone = _memberPhone.asStateFlow()
    fun setMemberPhone(name: String) {
        _memberPhone.value = name
    }


    private val _joinDate = MutableStateFlow(today())
    val joinDate = _joinDate.asStateFlow()
    fun setTaskDate(date: kotlinx.datetime.LocalDateTime) {
        _joinDate.value = date
    }


    private val _showTaskDatePickerDialog = MutableStateFlow(false)
    val showTaskDatePickerDialog = _showTaskDatePickerDialog.asStateFlow()
    fun setShowTaskDatePickerDialog(show: Boolean) {
        _showTaskDatePickerDialog.value = show
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            _eventsFlow.trySend(UiEvents.SnackbarEvent("Task added!"))
        }
    }


    private fun setTask(task: Task?) {
        _task.value = task
    }

    private val _task = MutableStateFlow<Task?>(null)
    val task = _task.asStateFlow()


    fun saveMemberToDb(){
        viewModelScope.launch {
            val names = memberName.value.split(" ")
            val firstName: String? = names.getOrNull(0)
            val lastName: String? = names.getOrNull(1)

            val nonNullableFirstName: String = firstName ?: ""
            val nonNullableLastName: String = lastName ?: ""


            _loginState.value = loginState.value.copy(isLoading = true)
            repository.insertMember(
                MemberEntity(
                    firstName = nonNullableFirstName,
                    lastName = nonNullableLastName,
                    contactInformation = memberPhone.value,
                    dateJoined = joinDate.value.date.toString(),
                    isBackedUp = false
                )
            )
            _loginState.value = loginState.value.copy(isLoading = false)

        }
    }

}
