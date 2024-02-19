package teka.android.chamaaapp.presentation.profile.presentation.account

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import teka.android.chamaaapp.presentation.profile.domain.model.Account
import teka.android.chamaaapp.presentation.profile.domain.model.User
import kotlinx.coroutines.flow.collectLatest
import teka.android.chamaaapp.R
import teka.android.chamaaapp.features.feature_auth.AuthViewModel
import teka.android.chamaaapp.navigation.AUTH_GRAPH_ROUTE
import teka.android.chamaaapp.navigation.ROOT_GRAPH_ROUTE
import teka.android.chamaaapp.navigation.Screen
import teka.android.chamaaapp.ui.theme.GrayColor
import teka.android.chamaaapp.ui.theme.Green
import teka.android.chamaaapp.ui.theme.GreenEnd
import teka.android.chamaaapp.ui.theme.MainWhiteColor
import teka.android.chamaaapp.ui.theme.MensColor
import teka.android.chamaaapp.ui.theme.YellowMain
import teka.android.chamaaapp.ui.theme.quicksand
import teka.android.chamaaapp.util.UiEvents
import java.util.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel(),
    navigator: NavController,
) {
    LaunchedEffect(key1 = true, block = {
        viewModel.getProfile()
    })
    val user = viewModel.profileState.value

    val isSyncing by viewModel.isSyncing.collectAsState()


//    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvents.SnackbarEvent -> {
                    event.message.let {
//                        scaffoldState.snackbarHostState.showSnackbar(
//                            message = it,
//                            duration = SnackbarDuration.Short
//                        )
                    }
                }
                is UiEvents.NavigateEvent -> {
                    navigator.navigate(event.route) {
                        popUpTo(Screen.ProfileScreen.route) {
                            inclusive = false
                        }
                        popUpTo(Screen.HomeScreen.route) {
                            inclusive = false
                        }
                        popUpTo(Screen.MembersScreen.route) {
                            inclusive = false
                        }
                        popUpTo(Screen.WalletScreen.route) {
                            inclusive = false
                        }
                    }
                }
            }
        }
    }

    Scaffold(
//        backgroundColor = Color.White,
//        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
//                elevation = 1.dp,
//                backgroundColor = Color.White,
                title = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "Profile Page",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = quicksand
                    )
                }
            )
        }
    ) {
        AccountScreenContent(
            user = user,
            profileViewModel = viewModel,
            onClickSignOut = {
                authViewModel.logout()
//                navHostController.navigate(ROOT_GRAPH_ROUTE)
//                viewModel.logout()
            },
            navigator = navigator
        )
    }
}

@Composable
private fun AccountScreenContent(
    user: User,
    profileViewModel: ProfileViewModel,
    onClickSignOut: () -> Unit,
    navigator: NavController
) {
    LazyColumn {
        item {
            Spacer(modifier = Modifier.height(65.dp))
        }
        item {
            UserItem(
                user = user,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .padding(4.dp)
            )
        }
        items(
            accountItems(navigator = navigator, profileViewModel = profileViewModel)
        ) { item ->
            Card(
                modifier = Modifier.padding(8.dp),
                border = BorderStroke(0.3.dp, GrayColor),
                shape = RoundedCornerShape(8.dp)
            ) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(MensColor)
                ){
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = item.title,
                                color = Color.Black,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = item.content,
                                color = Color.Black,
                                fontWeight = FontWeight.Light,
                                fontSize = 12.sp
                            )
                        }
                        IconButton(onClick = {
                                item.onClick()
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.ChevronRight,
                                contentDescription = null
                            )
                        }
                    }
                }

            }
        }
        item {
            Spacer(modifier =  Modifier.height(10.dp))
        }
        item {
            OutlinedButton(
                modifier = Modifier.padding(8.dp),
                onClick = onClickSignOut,
                shape = RoundedCornerShape(8),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Green,
//                            backgroundColor = Color.Transparent,
                ),
                border = BorderStroke(1.dp, Green),
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    text = "Sign Out",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun UserItem(
    user: User,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Row(
            modifier = Modifier.background(MensColor)
        ) {
            Image(
                painter = painterResource(R.drawable.asset11),
                contentDescription = null,
                modifier = Modifier
                    .padding(5.dp)
                    .weight(1f)
                    .clip(CircleShape)
                    .fillMaxHeight(),
                contentScale = ContentScale.Inside
            )
            Spacer(modifier = Modifier.width(5.dp))

            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(5.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "K.E.Y Chamaa",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
//                    text = "@${user.username}",
                    text = "Aricha",
                    color = Color.Black,
                    fontSize = 16.sp,
                    maxLines = 3,
                    fontWeight = FontWeight.Light
                )
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    modifier = Modifier.align(End),
                    onClick = {
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MainWhiteColor,
                        containerColor = Green
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(3.dp),
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center,
                        text = "Edit profile",
                        fontFamily = quicksand
                    )
                }
            }
        }
    }
}

private fun accountItems(
    navigator: NavController,
    profileViewModel: ProfileViewModel
): List<Account> = listOf(
    Account(
        title = "Add Account",
        content = "Add chamaa Group or Account",
        onClick = {
            println("Navigating to Add Account Screen")
            navigator.navigate(Screen.AddChamaaAccountScreen.route) {
                // Specify pop behavior if needed
            }
        }
    ),
    Account(
        title = "Fetch",
        content = "Fetch Data from cloud",
        onClick = {
            profileViewModel.fetchAndSaveAccountTypes()
            println("Syncing data from cloud")
        }
    ),
    Account(
        title = "Sync",
        content = "Back up data to cloud",
        onClick = {
            profileViewModel.syncRoomDbToRemote()
            println("Syncing data to cloud")
        }

    ),
    Account(
        title = "App Update",
        content = "In-App update",
        onClick = {
            println("Checking for app update")
        }

    )
)
