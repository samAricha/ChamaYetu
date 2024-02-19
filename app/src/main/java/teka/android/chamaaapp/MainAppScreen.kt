package teka.android.chamaaapp

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import teka.android.chamaaapp.navigation.MainNavGraph
import teka.android.chamaaapp.navigation.rememberAppState
import teka.android.chamaaapp.util.components.BottomNavigationBar


@OptIn(ExperimentalAnimationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainAppScreen() {
    val navController = rememberAnimatedNavController()
    val newBackStackEntry by navController.currentBackStackEntryAsState()
    val route = newBackStackEntry?.destination?.route
    val appState = rememberAppState(navHostController = navController)

    Scaffold(
        bottomBar = {
            if (appState.shouldShowBottomBar){
                BottomNavigationBar(navController = appState.navHostController)
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            MainNavGraph(navController = appState.navHostController)
        }
    }


}