package teka.android.chamaaapp.presentation.wallet

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import teka.android.chamaaapp.data.local.room.entities.MemberEntity
import teka.android.chamaaapp.domain.models.Task
import teka.android.chamaaapp.ui.theme.GreenEnd
import teka.android.chamaaapp.ui.theme.quicksand
import teka.android.chamaaapp.util.components.ContributionProgressCard
import teka.android.chamaaapp.util.components.ProgressIndicator
import teka.android.chamaaapp.util.components.SimpleTopAppBar



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import teka.android.chamaaapp.navigation.Screen
import teka.android.chamaaapp.ui.theme.Green
import teka.android.chamaaapp.util.components.ContributionListItemCard
import teka.android.chamaaapp.util.components.MemberListItemCard

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WalletHomeScreen(
    navController: NavController
){
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val walletViewModel : WalletViewModel = hiltViewModel()


    val isSyncing by walletViewModel.isSyncing.collectAsState()
    val fabClicked = remember { mutableStateOf(false) }


    val snackbarData by walletViewModel.snackbarData.collectAsState()

    if (snackbarData != null) {
        LaunchedEffect(snackbarData) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(snackbarData!!.message)
            }
            walletViewModel.clearSnackbar()
        }
    }

    val collections by walletViewModel.chamaaContributions.collectAsState()
    val contributions by walletViewModel.chamaaContributionsWithNames.collectAsState()
    val chamaaMembers by walletViewModel.chamaaMembers.collectAsState()

    Text(text = "WALLET SCREEN")



    Scaffold(
        topBar = {
            SimpleTopAppBar {
                Text(
                    text = "Wallet",
                    fontWeight = FontWeight.Bold,
                    fontFamily = quicksand,
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
            onClick = {
                fabClicked.value = true
                walletViewModel.syncRoomDbToRemote()
                navController.navigate(Screen.AddContributionScreen.route)
                fabClicked.value = false
            },
            containerColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Green
                )
            }
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
   ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ){
            LazyColumn {
                item {
                    Spacer(modifier = Modifier.height(45.dp))
                }
                item {
                    ContributionProgressCard(
                        contributions = contributions,
                        members = chamaaMembers
                        )
                }
                item {
                    TopPageSection()
                }
                items(contributions) { contribution ->

                    ContributionListItemCard(
                        contributionEntity = contribution,
                        onItemClick = {
//                            onNavigate.invoke(collection.id)
                        },
                        walletViewModel = walletViewModel
                    )

                }
            }


            if (isSyncing) {
                ProgressIndicator()
            }
        }
    }
}




@Preview
@Composable
fun TopPageSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column {
            Text(
                text = "Contributions",
                fontSize = 27.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = quicksand,
                fontWeight = FontWeight.Bold
            )

        }
    }
}



