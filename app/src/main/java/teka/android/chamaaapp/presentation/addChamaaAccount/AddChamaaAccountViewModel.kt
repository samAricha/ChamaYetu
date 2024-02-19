package teka.android.chamaaapp.presentation.addChamaaAccount

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
import teka.android.chamaaapp.data.local.room.entities.AccountTypeEntity
import teka.android.chamaaapp.data.local.room.entities.ChamaAccountEntity
import teka.android.chamaaapp.data.local.room.entities.ChamaEntity
import teka.android.chamaaapp.domain.models.LoadingState
import teka.android.chamaaapp.domain.models.Task
import teka.android.chamaaapp.util.UiEvents
import teka.android.chamaaapp.util.today
import javax.inject.Inject

@HiltViewModel
class AddChamaaAccountViewModel @Inject constructor(
    context: Context,
    private val repository: DbRepository
) : ViewModel() {

    private val _eventsFlow = Channel<UiEvents>(Channel.UNLIMITED)
    val eventsFlow = _eventsFlow.receiveAsFlow()

    private val _chamaaAccountTypes = MutableStateFlow<List<AccountTypeEntity>>(emptyList())
    val chamaaAccountTypes: StateFlow<List<AccountTypeEntity>> = _chamaaAccountTypes.asStateFlow()

    private val _selectedAccountTypeOption = MutableStateFlow<AccountTypeEntity?>(null)
    val selectedAccountTypeOption = _selectedAccountTypeOption.asStateFlow()

    init {
        fetchAllAccountTypes()
    }

    fun setSelectedAccountTypeOption(option: AccountTypeEntity) {
        _selectedAccountTypeOption.value = option
    }


    private val _loadingState = mutableStateOf(LoadingState())
    val loadingState: State<LoadingState> = _loadingState


    private val _chamaaName = MutableStateFlow("")
    val chamaaName = _chamaaName.asStateFlow()
    fun setChamaaName(name: String) {
        _chamaaName.value = name
    }

    private val _chamaaAccountName = MutableStateFlow("")
    val chamaaAccountName = _chamaaAccountName.asStateFlow()
    fun setChamaaAccountName(name: String) {
        _chamaaAccountName.value = name
    }



    private val _targetAmount = MutableStateFlow("")
    val targetAmount = _targetAmount.asStateFlow()
    fun setTargetAmount(name: String) {
        _targetAmount.value = name
    }


    private val _creationDate = MutableStateFlow(today())
    val creationDate = _creationDate.asStateFlow()
    fun setCreationDate(date: kotlinx.datetime.LocalDateTime) {
        _creationDate.value = date
    }


    private val _showCreationDatePickerDialog = MutableStateFlow(false)
    val showCreationDatePickerDialog = _showCreationDatePickerDialog.asStateFlow()
    fun setShowCreationDatePickerDialog(show: Boolean) {
        _showCreationDatePickerDialog.value = show
    }

    private fun fetchAllAccountTypes() {
        viewModelScope.launch {
            repository.allAccountTypes.collectLatest { accountTypes ->
                _chamaaAccountTypes.value = accountTypes
            }
        }
    }


    fun saveChamaaAccountToDb(){
        viewModelScope.launch {
            val name = chamaaName.value

            _loadingState.value = loadingState.value.copy(isLoading = true)
            val chamaaId = repository.insertChamaa(
                ChamaEntity(
                    chamaName = name,
                    chamaDescription = "",
                    dateFormed = creationDate.value.date.toString(),
                )
            )

            repository.insertChamaaAccount(
                ChamaAccountEntity(
                    chamaId = chamaaId,
                    accountName = chamaaAccountName.value,
                    accountTypeId = selectedAccountTypeOption.value?.accountTypeId.toString()
                )
            )
            _eventsFlow.trySend(UiEvents.SnackbarEvent("Account added!"))
            _loadingState.value = loadingState.value.copy(isLoading = false)

        }
    }

}
