package teka.android.chamaaapp.presentation.members

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import teka.android.chamaaapp.data.local.room.entities.MemberEntity
import teka.android.chamaaapp.navigation.Screen
import teka.android.chamaaapp.ui.theme.Green
import teka.android.chamaaapp.ui.theme.GreenEnd
import teka.android.chamaaapp.ui.theme.quicksand
import teka.android.chamaaapp.util.components.MemberListItemCard
import teka.android.chamaaapp.util.components.ProgressIndicator
import teka.android.chamaaapp.util.components.SimpleTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MemberHomeScreen(
    navController: NavController
){
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val membersViewModel : MemberHomeViewModel = hiltViewModel()




    val isSyncing by membersViewModel.isSyncing.collectAsState()
    val fabClicked = remember { mutableStateOf(false) }


    val snackbarData by membersViewModel.snackbarData.collectAsState()

    if (snackbarData != null) {
        LaunchedEffect(snackbarData) {
            membersViewModel.fetchAllMembers()
            coroutineScope.launch {
                snackbarHostState.showSnackbar(snackbarData!!.message)
            }
            membersViewModel.clearSnackbar()
        }
    }


    val collections by membersViewModel.chamaaMembers.collectAsState()


    Scaffold(
        topBar = {
            SimpleTopAppBar {
                Text(
                    text = "Members",
                    fontWeight = FontWeight.Bold,
                    fontFamily = quicksand,
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                fabClicked.value = true
//                membersViewModel.syncRoomDbToRemote()
                navController.navigate(Screen.AddMemberScreen.route)
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
        snackbarHost = { SnackbarHost(snackbarHostState) },

   ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ){
            LazyColumn {
                item { 
                    Spacer(modifier = Modifier.height(65.dp))
                }
                items(collections) { collection ->

                    MemberListItemCard(
                        memberEntity = collection,
                        onItemClick = {
//                            onNavigate.invoke(collection.id)
                        }
                    )

                }
            }


            if (isSyncing) {
                ProgressIndicator()
            }
        }
    }
}




