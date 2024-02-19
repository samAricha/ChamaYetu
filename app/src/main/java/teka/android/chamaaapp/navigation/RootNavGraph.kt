package teka.android.chamaaapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.delay
import teka.android.chamaaapp.MainAppScreen
import teka.android.chamaaapp.features.feature_auth.UserState
import teka.android.chamaaapp.features.feature_auth.presentation.login.LoginScreen
import teka.android.chamaaapp.util.components.ProgressIndicator

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(navController = navController,
        startDestination = startDestination,
        route = ROOT_GRAPH_ROUTE
    ){
        authNavGraph(navController = navController)

        composable(route = To_MAIN_GRAPH_ROUTE){
            var isLoading by remember { mutableStateOf(true) }
            val vm = UserState.current
            val isLoggedInState by vm.isLoggedInState.collectAsState(initial = null)


            LaunchedEffect(isLoggedInState) {
                if (isLoggedInState != null) {
                    isLoading = false
                }
            }

            if (isLoading) {
                ProgressIndicator()
            }


            if (isLoggedInState != null) {
                if (isLoggedInState as Boolean) {
                    MainAppScreen()
                } else {
                    authNavGraph(navController)
                }
            } else {
                if (isLoading) {
                    ProgressIndicator()
                }
            }
        }

    }
}