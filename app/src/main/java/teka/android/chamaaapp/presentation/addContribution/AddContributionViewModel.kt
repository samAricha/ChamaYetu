package teka.android.chamaaapp.presentation.addContribution

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import teka.android.chamaaapp.data.local.repository.DbRepository
import teka.android.chamaaapp.data.local.room.entities.ChamaAccountEntity
import teka.android.chamaaapp.data.local.room.entities.ContributionEntity
import teka.android.chamaaapp.data.local.room.entities.MemberEntity
import teka.android.chamaaapp.domain.models.Task
import teka.android.chamaaapp.features.feature_auth.presentation.login.LoginState
import teka.android.chamaaapp.util.UiEvents
import teka.android.chamaaapp.util.today
import javax.inject.Inject

@HiltViewModel
class AddContributionViewModel @Inject constructor(
    context: Context,
    private val repository: DbRepository,
    ) : ViewModel() {

    private val _chamaaMembers = MutableStateFlow<List<MemberEntity>>(emptyList())
    val chamaaMembers: StateFlow<List<MemberEntity>> = _chamaaMembers.asStateFlow()

    private val _chamaaAccounts = MutableStateFlow<List<ChamaAccountEntity>>(emptyList())
    val chamaaAccounts: StateFlow<List<ChamaAccountEntity>> = _chamaaAccounts.asStateFlow()

    private val _eventsFlow = Channel<UiEvents>(Channel.UNLIMITED)
    val eventsFlow = _eventsFlow.receiveAsFlow()

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    init {
        fetchAllMembers()
        fetchAllAccounts()
    }

    private val _contributionAmount = mutableStateOf("")
    val contributionAmount: State<String> = _contributionAmount
    fun setContributionAmount(amount: String) {
        _contributionAmount.value = amount
    }

    private val _memberPhone = mutableStateOf("")
    val memberPhone: State<String> = _memberPhone
    fun setPhoneNumber(name: String) {
        _memberPhone.value = name
    }



    private val _contributionDate = MutableStateFlow(today())
    val contributionDate = _contributionDate.asStateFlow()
    fun setContributionDate(date: kotlinx.datetime.LocalDateTime) {
        _contributionDate.value = date
    }

    private val _selectedOption = MutableStateFlow<MemberEntity?>(null)
    val selectedOption = _selectedOption.asStateFlow()
    fun setSelectedOption(option: MemberEntity) {
        _selectedOption.value = option
    }

    private val _selectedAccountOption = MutableStateFlow<ChamaAccountEntity?>(null)
    val selectedAccountOption = _selectedAccountOption.asStateFlow()
    fun setSelectedAccountOption(option: ChamaAccountEntity) {
        _selectedAccountOption.value = option
    }



    private val _showStartTimeInputDialog = MutableStateFlow(false)
    val showStartTimeInputDialog = _showStartTimeInputDialog.asStateFlow()
    fun setShowStartTimeInputDialog(show: Boolean) {
        _showStartTimeInputDialog.value = show
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

    private fun fetchAllMembers() {
        viewModelScope.launch {
            repository.allMembers.collectLatest { members ->
                _chamaaMembers.value = members
            }
        }
    }

    private fun fetchAllAccounts() {
        viewModelScope.launch {
            repository.allChamaAccounts.collectLatest { accounts ->
                _chamaaAccounts.value = accounts
            }
        }
    }

    fun saveContributionToDb(){
        viewModelScope.launch {
            _loginState.value = loginState.value.copy(isLoading = true)
            repository.insertContribution(
                ContributionEntity(
                    memberId = selectedOption.value!!.memberId,
                    chamaAccountId = selectedAccountOption.value!!.accountId,
                    contributionDate = contributionDate.value.date.toString(),
                    contributionAmount = contributionAmount.value,
                )
            )
            _loginState.value = loginState.value.copy(isLoading = false)

        }
    }

}
