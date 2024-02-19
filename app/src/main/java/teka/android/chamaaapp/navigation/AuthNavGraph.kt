package teka.android.chamaaapp.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import teka.android.chamaaapp.features.feature_auth.presentation.auth_dashboard.AuthDashboardScreen
import teka.android.chamaaapp.features.feature_auth.presentation.forgot_password.ForgotPasswordScreen
import teka.android.chamaaapp.features.feature_auth.presentation.login.LoginScreen
import teka.android.chamaaapp.features.feature_auth.presentation.register.RegisterScreen

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
){

    navigation(
        startDestination = Screen.AuthDashboardScreen.route,
        route = AUTH_GRAPH_ROUTE
    ){


        composable(
            route = Screen.LoginScreen.route
        ) {
            LoginScreen(
                navigator = navController
            )
        }

        composable(
            route = Screen.RegisterScreen.route
        ) {
            RegisterScreen(
                navigator = navController,
            )
        }

        composable(
            route = Screen.AuthDashboardScreen.route
        ) {
            AuthDashboardScreen(
                navigator = navController,
            )
        }

        composable(
            route = Screen.ForgotPasswordScreen.route
        ) {
            ForgotPasswordScreen()
        }

    }
}