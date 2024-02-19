package teka.android.chamaaapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import teka.android.chamaaapp.features.feature_auth.AuthViewModel
import teka.android.chamaaapp.features.feature_auth.UserState
import teka.android.chamaaapp.features.feature_auth.presentation.splash.SplashViewModel
import teka.android.chamaaapp.navigation.RootNavGraph
import teka.android.chamaaapp.presentation.home.HomeScreen
import teka.android.chamaaapp.ui.theme.ChamaaAppTheme
import teka.android.chamaaapp.util.components.SetBarColor
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val userState by viewModels<AuthViewModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        setContent {
            val splashViewModel: SplashViewModel = hiltViewModel()
            splashViewModel.startDestination.value?.let { Log.d("TAG3", it) }

            CompositionLocalProvider(UserState provides userState) {
                ChamaaAppTheme {
                    SetBarColor(color = MaterialTheme.colorScheme.background)

                    val vm = UserState.current
                    val isLoggedInState by vm.isLoggedInState.collectAsState(initial = null)

                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val navController = rememberAnimatedNavController()
                        Box() {
                            val startDestination by splashViewModel.startDestination
                            startDestination?.let {
                                RootNavGraph(
                                    navController = rememberAnimatedNavController(),
                                    startDestination = it
                                )
                            }

                        }
                    }
                }

            }
        }
    }
}
