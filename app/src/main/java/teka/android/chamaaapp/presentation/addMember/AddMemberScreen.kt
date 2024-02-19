package teka.android.chamaaapp.presentation.addMember

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
import androidx.compose.ui.text.input.KeyboardCapitalization
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import teka.android.chamaaapp.features.feature_auth.presentation.login.LoginState
import teka.android.chamaaapp.navigation.Screen
import teka.android.chamaaapp.ui.theme.MainWhiteColor
import teka.android.chamaaapp.ui.theme.quicksand
import teka.android.chamaaapp.util.logic.selectedDateMillisToLocalDateTime

@OptIn(ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun AddMemberScreen(
    navController: NavController,
    taskId: Int? = null,
) {
    val screenModel: AddMemberViewModel = hiltViewModel()

    val loginState = screenModel.loginState.value


    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val showTaskDatePickerDialog = screenModel.showTaskDatePickerDialog.collectAsState().value
    val memberName = screenModel.memberName.collectAsState()
    val memberPhone = screenModel.memberPhone.collectAsState()
    val taskToUpdate = screenModel.task.collectAsState().value
    val joinDate = screenModel.joinDate.collectAsState().value




    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Clock.System.now().toEpochMilliseconds(),
    )


    if (showTaskDatePickerDialog) {
        teka.android.chamaaapp.presentation.addContribution.JoiningDateDatePicker(
            datePickerState = datePickerState,
            dismiss = {
                screenModel.setShowTaskDatePickerDialog(false)
            },
            onConfirmDate = {
                screenModel.setTaskDate(it)
                screenModel.setShowTaskDatePickerDialog(false)
            },
        )
    }
    Text(text = "ADD MEMBER SCREEN")

    AddMemberScreenContent(
        snackbarHostState = snackbarHostState,
        memberName = memberName.value,
        memberPhone = memberPhone.value,
        taskDate = joinDate,
        loginState = loginState,
        onMemberNameChange = {
            screenModel.setMemberName(it)
        },
        onMemberPhoneChange = {
            screenModel.setMemberPhone(it)
        },
        onClickPickDate = {
            screenModel.setShowTaskDatePickerDialog(true)
        },
        onClickAddBtn = {
            screenModel.saveMemberToDb()
            navController.navigate(Screen.MembersScreen.route)
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddMemberScreenContent(
    snackbarHostState: SnackbarHostState,
    memberName: String,
    memberPhone: String,
    loginState: LoginState,
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
                Text(
                    text = "Add Member",
                    fontFamily = quicksand)
            }
        },
    ) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 65.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item {
                CustomInputTextField(
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3,
                    label = {
                        Text(
                            text = "Member Name",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                fontFamily = quicksand
                            ),
                        )
                    },
                    value = TextFieldState(text = memberName),
                    onValueChange = onMemberNameChange,
                    placeholder = {
                        Text(
                            text = "Enter Member Name",
                            style = MaterialTheme.typography.titleSmall,

                        )
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Words,
                    ),
                    textStyle = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 16.sp,
                    ),
                )
            }
            item {
                Spacer(modifier = Modifier.height(10.dp))
            }
            item {
                CustomInputTextField(
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3,
                    label = {
                        Text(
                            text = "Phone",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                fontFamily = quicksand
                            ),
                        )
                    },
                    value = TextFieldState(text = memberPhone),
                    onValueChange = onMemberPhoneChange,
                    placeholder = {
                        Text(
                            text = "Enter Phone Number",
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
                            text = "Date Joined",
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
                        text = "Add Member",
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

