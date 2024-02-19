package teka.android.chamaaapp.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.PeopleAlt
import androidx.compose.material.icons.rounded.Wallet
import androidx.compose.ui.graphics.vector.ImageVector
import teka.android.chamaaapp.R

enum class BottomBarRoutes(
    val id: Int,
    @StringRes val title: Int,
    val routes: String,
    val icon: ImageVector
) {
    HOME(1, R.string.home, Screen.HomeScreen.route, Icons.Rounded.Home),
    WALLET(2, R.string.wallet, Screen.WalletScreen.route, Icons.Rounded.Wallet),
    MEMBERS(3, R.string.members, Screen.MembersScreen.route, Icons.Rounded.PeopleAlt),
    PROFILE(3, R.string.profile, Screen.ProfileScreen.route, Icons.Rounded.AccountCircle)
}