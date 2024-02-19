package teka.android.chamaaapp.navigation

import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun rememberAppState(
    navHostController: NavHostController,
) =   remember(navHostController) {
    AppState(navHostController)
}

@Stable
class AppState(
    val navHostController: NavHostController,
) {
    private val bottomBarRoutes = BottomBarRoutes.entries.map { it.routes }

    val shouldShowBottomBar: Boolean
        @Composable get() =
            navHostController.currentBackStackEntryAsState().value?.destination?.route in bottomBarRoutes


    val currentRoute: String?
        get() = navHostController.currentDestination?.route

    fun upPress() {
        navHostController.navigateUp()
    }

    fun navigateToBottomBarRoute(route: String) {
        if (route != currentRoute) {
            navHostController.navigate(route) {
                launchSingleTop = true
                restoreState = true
                popUpTo(findStartDestination(navHostController.graph).id) {
                    saveState = true
                }
            }
        }
    }
}


private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED

private val NavGraph.startDestination: NavDestination?
    get() = findNode(startDestinationId)

private tailrec fun findStartDestination(graph: NavDestination): NavDestination {
    return if (graph is NavGraph) findStartDestination(graph.startDestination!!) else graph
}