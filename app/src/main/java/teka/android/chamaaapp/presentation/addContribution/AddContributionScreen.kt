package teka.android.chamaaapp.presentation.addContribution

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import teka.android.chamaaapp.R
import teka.android.chamaaapp.domain.models.TextFieldState
import teka.android.chamaaapp.util.components.CustomDateBoxField
import teka.android.chamaaapp.util.components.CustomInputTextField
import teka.android.chamaaapp.util.components.SimpleTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.DatePickerState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import teka.android.chamaaapp.data.local.room.entities.ChamaAccountEntity
import teka.android.chamaaapp.data.local.room.entities.MemberEntity
import teka.android.chamaaapp.features.feature_auth.presentation.login.LoginState
import teka.android.chamaaapp.navigation.Screen
import teka.android.chamaaapp.ui.theme.MainWhiteColor
import teka.android.chamaaapp.ui.theme.quicksand
import teka.android.chamaaapp.util.components.CustomDropDown
import teka.android.chamaaapp.util.components.CustomDropDown2
import teka.android.chamaaapp.util.logic.selectedDateMillisToLocalDateTime

@OptIn(ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun AddContributionScreen(
    navController: NavController,
    taskId: Int? = null,
) {
    val screenModel: AddContributionViewModel = hiltViewModel()

    val chamaaMembersList by screenModel.chamaaMembers.collectAsState()
    val accountsList by screenModel.chamaaAccounts.collectAsState()
    val selectedMember = screenModel.selectedOption.collectAsState().value
    val selectedAccount = screenModel.selectedAccountOption.collectAsState().value



    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val showStartTimeInputDialog = screenModel.showStartTimeInputDialog.collectAsState().value
    val showTaskDatePickerDialog = screenModel.showTaskDatePickerDialog.collectAsState().value
    val contributionAmount = screenModel.contributionAmount.value
    val memberPhone = screenModel.memberPhone.value
    val taskToUpdate = screenModel.task.collectAsState().value
    val taskDate = screenModel.contributionDate.collectAsState().value

    val loginState = screenModel.loginState.value




    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Clock.System.now().toEpochMilliseconds(),
    )


    if (showTaskDatePickerDialog) {
        JoiningDateDatePicker(
            datePickerState = datePickerState,
            dismiss = {
                screenModel.setShowTaskDatePickerDialog(false)
            },
            onConfirmDate = {
                screenModel.setContributionDate(it)
                screenModel.setShowTaskDatePickerDialog(false)
            },
        )
    }
    Text(text = "ADD MEMBER SCREEN")

    AddContributionScreenContent(
        snackbarHostState = snackbarHostState,
        contributionAmount = contributionAmount,
        memberPhone = memberPhone,
        loginState = loginState,
        membersList = chamaaMembersList,
        accountsList = accountsList,
        selectedMember = selectedMember,
        selectedAccount = selectedAccount,
        taskDate = taskDate,
        onMemberNameChange = {
            screenModel.setContributionAmount(it)
        },
        onMemberPhoneChange = {
            screenModel.setPhoneNumber(it)
        },
        onSelectedMemberChange = {
            screenModel.setSelectedOption(it)
        },
        onSelectedAccountChange = {
            screenModel.setSelectedAccountOption(it)
        },
        onClickPickDate = {
            screenModel.setShowTaskDatePickerDialog(true)
        },
        onClickAddBtn = {
            screenModel.saveContributionToDb()
            navController.navigate(Screen.WalletScreen.route)
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddContributionScreenContent(
    snackbarHostState: SnackbarHostState,
    contributionAmount: String,
    memberPhone: String,
    loginState: LoginState,
    selectedMember: MemberEntity?,
    selectedAccount: ChamaAccountEntity?,
    membersList: List<MemberEntity>,
    accountsList: List<ChamaAccountEntity>,
    onSelectedMemberChange: (MemberEntity) -> Unit,
    onSelectedAccountChange: (ChamaAccountEntity) -> Unit,
    onMemberNameChange: (String) -> Unit,
    onMemberPhoneChange: (String) -> Unit,
    onClickPickDate: () -> Unit,
    onClickAddBtn: () -> Unit,
    taskDate: LocalDateTime,
) {
    Scaffold(
        snackbarHost = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter, // Change to your desired position
            ) {
                SnackbarHost(
                    hostState = snackbarHostState,
                    snackbar = {
                        Card(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .clickable {
                                    snackbarHostState.currentSnackbarData?.dismiss()
                                },
                            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                            ),
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth(.85f),
                                    text = it.visuals.message,
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        color = MaterialTheme.colorScheme.onPrimary,
                                    ),
                                )
                                Image(
                                    modifier = Modifier
                                        .size(32.dp),
                                    painter = painterResource(R.drawable.ic_complete),
                                    contentDescription = "Task Options",
                                )
                            }
                        }
                    },
                )
            }
        },
        topBar = {
            SimpleTopAppBar {
                Text(text = "Record Contribution")
            }
        },
    ) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 65.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item {
                CustomDropDown(
                    label = {
                        Text(
                            text = "Member",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                fontFamily = quicksand
                            ),
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    options = membersList,
                    selectedOption = TextFieldState("${selectedMember?.firstName.orEmpty()} ${selectedMember?.lastName.orEmpty()}"),
                    onOptionSelected = onSelectedMemberChange,
                    textStyle = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 16.sp,
                    ),
                )
            }
            item {
                CustomDropDown2(
                    label = {
                        Text(
                            text = "Account",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                fontFamily = quicksand
                            ),
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    options = accountsList,
                    selectedOption = TextFieldState(selectedAccount?.accountName.orEmpty()),
                    onOptionSelected = onSelectedAccountChange,
                    optionTextProvider = { account:ChamaAccountEntity ->
                        Text(
                            text = account.accountName,
                            style = MaterialTheme.typography.labelLarge,
                        )
                    },
                    textStyle = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 16.sp,
                    ),
                )
            }
            item {
                CustomInputTextField(
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3,
                    label = {
                        Text(
                            text = "Amount",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                fontFamily = quicksand
                            ),
                        )
                    },
                    value = TextFieldState(text = contributionAmount),
                    onValueChange = onMemberNameChange,
                    placeholder = {
                        Text(
                            text = "Enter Amount",
                            style = MaterialTheme.typography.titleSmall,
                        )
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    textStyle = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 16.sp,
                    ),
                )
            }
            item {
                Spacer(modifier = Modifier.height(10.dp))
            }


            item {
                CustomDateBoxField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = {
                        Text(
                            text = "Date Contributed",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                fontFamily = quicksand
                            ),
                        )
                    },
                    currentTextState = TextFieldState(
                        text = taskDate.date.toString(),
                    ),
                    onClick = onClickPickDate,
                    textStyle = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 16.sp,
                    ),
                )
            }
            item { 
                Spacer(modifier = Modifier.height(28.dp))
            }
            item {
                Button(
                    onClick = onClickAddBtn,
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = MaterialTheme.shapes.extraLarge
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        textAlign = TextAlign.Center,
                        text = "Add Contribution",
                        color = MainWhiteColor,
                        fontFamily = quicksand,
                        fontWeight = FontWeight.ExtraBold
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
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoiningDateDatePicker(
    datePickerState: DatePickerState,
    dismiss: () -> Unit,
    onConfirmDate: (LocalDateTime) -> Unit,
) {
    DatePickerDialog(
        onDismissRequest = { dismiss() },
        dismissButton = {
            TextButton(onClick = dismiss) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmDate(datePickerState.selectedDateMillis.selectedDateMillisToLocalDateTime())
                    dismiss()
                },
            ) {
                Text(text = "OK")
            }
        },
    ) {
        DatePicker(state = datePickerState)
    }
}

