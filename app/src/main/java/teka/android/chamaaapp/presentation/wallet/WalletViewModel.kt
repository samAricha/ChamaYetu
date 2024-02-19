package teka.android.chamaaapp.presentation.wallet


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
import teka.android.chamaaapp.data.local.room.entities.ContributionWithMemberName
import teka.android.chamaaapp.data.local.room.entities.MemberEntity
import teka.android.chamaaapp.data.room_remote_sync.RemoteDataUpdater
import teka.android.chamaaapp.data.room_remote_sync.UpdateResult
import teka.android.chamaaapp.presentation.members.SnackbarData
import javax.inject.Inject

data class SnackbarData(val message: String)


@HiltViewModel
class WalletViewModel @Inject constructor(
    private val repository: DbRepository,
    private val remoteDataUpdater: RemoteDataUpdater
): ViewModel() {

    private val _chamaaContributions = MutableStateFlow<List<ContributionEntity>>(emptyList())
    val chamaaContributions: StateFlow<List<ContributionEntity>> = _chamaaContributions.asStateFlow()

    private val _chamaaMembers = MutableStateFlow<List<MemberEntity>>(emptyList())
    val chamaaMembers: StateFlow<List<MemberEntity>> = _chamaaMembers.asStateFlow()

    private val _chamaaContributionsWithNames = MutableStateFlow<List<ContributionWithMemberName>>(emptyList())
    val chamaaContributionsWithNames: StateFlow<List<ContributionWithMemberName>> = _chamaaContributionsWithNames.asStateFlow()


    private val _isSyncing = MutableStateFlow(false)
    val isSyncing: StateFlow<Boolean> = _isSyncing

    private val _snackbarData = MutableStateFlow<SnackbarData?>(null)
    val snackbarData: StateFlow<SnackbarData?> = _snackbarData



    init {
        viewModelInitialization()
    }

    fun viewModelInitialization(){
        fetchContributions()
        fetchContributorName()
        fetchMembers()
    }

    // Fetch and update milk collections in your ViewModel
    private fun fetchContributions() {
        viewModelScope.launch {
            repository.allContributions.collectLatest { contributions ->
                _chamaaContributions.value = contributions
            }
        }
    }

    private fun fetchMembers() {
        viewModelScope.launch {
            repository.allMembers.collectLatest { members ->
                _chamaaMembers.value = members
            }
        }
    }

    private fun fetchContributorName() {
        viewModelScope.launch {
            try {
                repository.getAllContributionsWithMemberName().collectLatest { contribution ->
                    _chamaaContributionsWithNames.value = contribution
                }
            } catch (e: Exception) {
                // Handle the exception, log, or perform error handling
                // For example, you might want to set _contributorName.value to null or handle the error in a different way
                _chamaaContributionsWithNames.value = emptyList()
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

                val notBackedUpContributionEntities = chamaaContributions.value.filter { contributionEntity ->
                    !contributionEntity.isBackedUp
                }

                val result =remoteDataUpdater.backupContributionData(notBackedUpContributionEntities, repository)

                when (result) {
                    is UpdateResult.Success -> {
                        _snackbarData.value = SnackbarData(result.message)
                        showSnackbar(_snackbarData.value!!.message)
                        fetchContributions()
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