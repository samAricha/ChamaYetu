package teka.android.chamaaapp.presentation.members


import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import teka.android.chamaaapp.data.local.repository.DbRepository
import teka.android.chamaaapp.data.local.room.entities.ContributionEntity
import teka.android.chamaaapp.data.local.room.entities.InvestmentEntity
import teka.android.chamaaapp.data.local.room.entities.MemberEntity
import teka.android.chamaaapp.data.room_remote_sync.RemoteDataUpdater
import teka.android.chamaaapp.data.room_remote_sync.UpdateResult
import javax.inject.Inject

data class SnackbarData(val message: String)


@HiltViewModel
class MemberHomeViewModel @Inject constructor(
    private val repository: DbRepository,
    private val remoteDataUpdater: RemoteDataUpdater
): ViewModel() {

    private val _chamaaMembers = MutableStateFlow<List<MemberEntity>>(emptyList())
    val chamaaMembers: StateFlow<List<MemberEntity>> = _chamaaMembers.asStateFlow()



    private val _isSyncing = MutableStateFlow(false)
    val isSyncing: StateFlow<Boolean> = _isSyncing


    private val _snackbarData = MutableStateFlow<SnackbarData?>(null)
    val snackbarData: StateFlow<SnackbarData?> = _snackbarData



    init {
        viewModelInitialization()
    }

    private fun viewModelInitialization(){
        fetchAllMembers()
    }

    // Fetch and update milk collections in your ViewModel
    fun fetchAllMembers() {
        viewModelScope.launch {
            repository.allMembers.collectLatest { members -> _chamaaMembers.value = members
            }
        }
    }


    // Function to trigger a Snackbar
    fun showSnackbar(message: String) {
        _snackbarData.value = SnackbarData(message)
    }

    // Function to clear the Snackbar
    fun clearSnackbar() {
        _snackbarData.value = null
    }




    fun syncRoomDbToRemote() {
        viewModelScope.launch {
            _isSyncing.value = true // Set isSyncing to true when synchronization starts
            try {

                val notBackedUpChamaaEntities = chamaaMembers.value.filter { chamaaEntity ->
                    !chamaaEntity.isBackedUp
                }

                val result =remoteDataUpdater.backupMemberData(notBackedUpChamaaEntities, repository)

                when (result) {
                    is UpdateResult.Success -> {
                        _snackbarData.value = SnackbarData(result.message)
                        showSnackbar(_snackbarData.value!!.message)
                        fetchAllMembers()
                    }
                    is UpdateResult.Failure -> {
                        _snackbarData.value = SnackbarData(result.errorMessage)
                        showSnackbar(_snackbarData.value!!.message)
                    }
                }
            } catch (e: Exception) {
                // Handle synchronization failure
            } finally {
                _isSyncing.value = false // Set isSyncing back to false when synchronization is done
            }
        }
    }
}