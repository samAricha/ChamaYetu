package teka.android.chamaaapp.util.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.PeopleAlt
import androidx.compose.material.icons.rounded.Wallet
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import teka.android.chamaaapp.domain.data.BottomNavigation
import teka.android.chamaaapp.navigation.Screen
import teka.android.chamaaapp.ui.theme.GrayColor
import teka.android.chamaaapp.ui.theme.Green
import teka.android.chamaaapp.ui.theme.GreenBall
import teka.android.chamaaapp.ui.theme.GreenEnd
import teka.android.chamaaapp.ui.theme.MensColor
import teka.android.chamaaapp.ui.theme.WomensColor

val items = listOf(
    BottomNavigation(
        title = "Home",
        icon = Icons.Rounded.Home,
        route = Screen.HomeScreen.route
    ),

    BottomNavigation(
        title = "Wallet",
        icon = Icons.Rounded.Wallet,
        route = Screen.WalletScreen.route
    ),

    BottomNavigation(
        title = "Members",
        icon = Icons.Rounded.PeopleAlt,
        route = Screen.MembersScreen.route
    ),

    BottomNavigation(
        title = "Profile",
        icon = Icons.Rounded.AccountCircle,
        route = Screen.ProfileScreen.route
    )
)

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 5.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        Column {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp),
                color = GrayColor
            )
            Row(
                modifier = Modifier.background(Color.White)
            ) {

                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = GreenBall,
                            unselectedIconColor = GrayColor,
                            selectedIconColor = Green
                        ),
                        selected = currentRoute == item.route,
                        onClick = {
                                  navController.navigate(item.route)
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        },
                        label = {
                            Text(
                                text = item.title,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    )
                }

            }
        }
    }
}


























