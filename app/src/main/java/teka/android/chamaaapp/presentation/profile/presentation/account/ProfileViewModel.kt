package teka.android.chamaaapp.presentation.profile.presentation.account

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import teka.android.chamaaapp.presentation.profile.data.repository.ProfileRepository
import teka.android.chamaaapp.presentation.profile.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import teka.android.chamaaapp.data.local.repository.DbRepository
import teka.android.chamaaapp.data.local.room.entities.ChamaAccountEntity
import teka.android.chamaaapp.data.local.room.entities.ChamaEntity
import teka.android.chamaaapp.data.local.room.entities.ContributionEntity
import teka.android.chamaaapp.data.local.room.entities.MemberEntity
import teka.android.chamaaapp.data.room_remote_sync.FetchRemoteData
import teka.android.chamaaapp.data.room_remote_sync.RemoteDataUpdater
import teka.android.chamaaapp.data.room_remote_sync.UpdateResult
import teka.android.chamaaapp.features.feature_auth.data.dto.UserResponseDto
import teka.android.chamaaapp.features.feature_auth.domain.use_case.LogoutUseCase
import teka.android.chamaaapp.navigation.Screen
import teka.android.chamaaapp.presentation.members.SnackbarData
import teka.android.chamaaapp.util.ResultResource
import teka.android.chamaaapp.util.UiEvents
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dbRepository: DbRepository,
    private val profileRepository: ProfileRepository,
    private val logoutUseCase: LogoutUseCase,
    private val remoteDataUpdater: RemoteDataUpdater,
    private val gson: Gson,
) : ViewModel() {

    private val fetchRemoteData = FetchRemoteData()


    private val _profileState = mutableStateOf(User())
    val profileState: State<User> = _profileState

    private val _isSyncing = MutableStateFlow(false)
    val isSyncing: StateFlow<Boolean> = _isSyncing

    private val _snackbarData = MutableStateFlow<SnackbarData?>(null)
    val snackbarData: StateFlow<SnackbarData?> = _snackbarData

    private val _chamaaMembers = MutableStateFlow<List<MemberEntity>>(emptyList())
    val chamaaMembers: StateFlow<List<MemberEntity>> = _chamaaMembers.asStateFlow()

    private val _contributions = MutableStateFlow<List<ContributionEntity>>(emptyList())
    val contributions: StateFlow<List<ContributionEntity>> = _contributions.asStateFlow()

    private val _chamaaAccounts = MutableStateFlow<List<ChamaAccountEntity>>(emptyList())
    val chamaaAccounts: StateFlow<List<ChamaAccountEntity>> = _chamaaAccounts.asStateFlow()

    private val _chamaas = MutableStateFlow<List<ChamaEntity>>(emptyList())
    val chamaas: StateFlow<List<ChamaEntity>> = _chamaas.asStateFlow()

    init {
        viewModelInitialization()
    }

    private fun viewModelInitialization(){
        fetchAllMembers()
    }

    private fun viewModelBackupDataFetch(){
        fetchAllChamaas()
        fetchAllChamaaAccounts()
        fetchAllMembers()
        fetchAllContributions()
    }

    fun fetchAllMembers() {
        viewModelScope.launch {
            dbRepository.allMembers.collectLatest { members -> _chamaaMembers.value = members
            }
        }
    }
    fun fetchAllContributions() {
        viewModelScope.launch {
            dbRepository.allContributions.collectLatest { contributions -> _contributions.value = contributions
            }
        }
    }
    fun fetchAllChamaaAccounts() {
        viewModelScope.launch {
            dbRepository.allChamaAccounts.collectLatest { chamaaAccounts -> _chamaaAccounts.value = chamaaAccounts
            }
        }
    }
    fun fetchAllChamaas() {
        viewModelScope.launch {
            dbRepository.allChamas.collectLatest { chamaas -> _chamaas.value = chamaas
            }
        }
    }

    fun showSnackbar(message: String) {
        _snackbarData.value = SnackbarData(message)
    }

    fun getProfile() {
        viewModelScope.launch {
            profileRepository.getUserProfile().collectLatest { data ->
                Timber.d("Data: $data")
                val user = gson.fromJson(data, UserResponseDto::class.java)
//                _profileState.value = user.toDomain()
            }
        }
    }

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()


    fun fetchAndSaveAccountTypes() {
        viewModelScope.launch {

            val result = fetchRemoteData.fetchAndSaveAccountTypes(dbRepository)
            when (result) {
                is ResultResource.Success -> {
                    _eventFlow.emit(
                        UiEvents.SnackbarEvent(
                            message = result.message ?: "Successfully fetched Account Types"
                        )
                    )
                    Log.d("Success", "Account types fetched and saved successfully.")
                }
                is ResultResource.Error -> {
                    _eventFlow.emit(
                        UiEvents.SnackbarEvent(
                            message = result.message ?: "Error fetching Account Types"
                        )
                    )
                    Log.e("Error", "Failed to fetch and save account types: ${result.message}")
                }
                is ResultResource.Loading -> {
                    // Handle loading case (if needed)
                    Log.d("Loading", "Fetching and saving account types...")
                }
            }


        }
    }



    fun syncRoomDbToRemote() {
        viewModelScope.launch {
            _isSyncing.value = true // Set isSyncing to true when synchronization starts

            //getAllData to screen first
            viewModelBackupDataFetch()


            try {

                val notBackedUpChamaaEntities = chamaas.value.filter { chamaaEntity ->
                    !chamaaEntity.isBackedUp
                }

                val notBackedUpChamaaAccountEntities = chamaaAccounts.value.filter { chamaaAccountEntity ->
                    !chamaaAccountEntity.isBackedUp
                }
                val notBackedUpChamaaMemberEntities = chamaaMembers.value.filter { chamaaMemberEntity ->
                    !chamaaMemberEntity.isBackedUp
                }
                val notBackedUpChamaaContributionEntities = contributions.value.filter { chamaaContributionEntity ->
                    !chamaaContributionEntity.isBackedUp
                }

                val result1 =remoteDataUpdater.backupChamaasAndAccountData(
                    notBackedUpChamaaEntities,
                    notBackedUpChamaaAccountEntities,
                    dbRepository
                )
                val result2 =remoteDataUpdater.backupMemberData(notBackedUpChamaaMemberEntities, dbRepository)
                val result3 =remoteDataUpdater.backupContributionData(notBackedUpChamaaContributionEntities, dbRepository)

                when (result1) {
                    is UpdateResult.Success -> {
                        _snackbarData.value = SnackbarData(result1.message)
                        showSnackbar(_snackbarData.value!!.message)
                        fetchAllMembers()
                    }
                    is UpdateResult.Failure -> {
                        _snackbarData.value = SnackbarData(result1.errorMessage)
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


    fun logout() {
        viewModelScope.launch {
            val result = logoutUseCase()

            Timber.d("MemberCountResult: ${result.message}")
            when (result) {
                is ResultResource.Success -> {
                    _eventFlow.emit(
                        UiEvents.NavigateEvent(route = Screen.AuthDashboardScreen.route)
                    )
                }
                is ResultResource.Error -> {
                    _eventFlow.emit(
                        UiEvents.SnackbarEvent(
                            message = result.message ?: "Unknown error occurred"
                        )
                    )
                }
                else -> {}
            }
        }
    }
}