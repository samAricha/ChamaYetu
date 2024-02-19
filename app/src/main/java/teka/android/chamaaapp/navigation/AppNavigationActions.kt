package teka.android.chamaaapp.navigation

import androidx.navigation.NavHostController
import teka.android.chamaaapp.navigation.AUTH_GRAPH_ROUTE

class AppNavigationActions(private val navController: NavHostController) {

    fun navigateToHome() {
//        navController.navigate(Screen.DashboardScreen.route) {
//            popUpTo(Screen.Home.route)
//        }
    }

    fun navigateToProductsHome() {
//        navController.navigate(Screen.ProductionHome.route) {
//            launchSingleTop = true
//            restoreState = true
//        }
    }

    fun logoutNav() {
        navController.navigate(AUTH_GRAPH_ROUTE) {
//            popUpTo(Screen.Login.route)
        }
    }

}