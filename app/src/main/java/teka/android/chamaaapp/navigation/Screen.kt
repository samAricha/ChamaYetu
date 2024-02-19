package teka.android.chamaaapp.navigation

const val ROOT_GRAPH_ROUTE = "root_graph_route"
const val AUTH_GRAPH_ROUTE = "auth_graph_route"
const val MAIN_GRAPH_ROUTE = "main_graph_route"
const val To_MAIN_GRAPH_ROUTE = "to_main_graph_route"


sealed class Screen(val route: String) {
    object HomeScreen : Screen(route = "home_screen")
    object WalletScreen : Screen(route = "wallet_screen")
    object MembersScreen : Screen(route = "members_screen")
    object AddMemberScreen : Screen(route = "add_member_screen")
    object AddContributionScreen : Screen(route = "add_contribution_screen")
    object AddChamaaAccountScreen : Screen(route = "add_chamaa_account_screen")
    object ProfileScreen : Screen(route = "profile_screen")





    object SplashScreen : Screen(route = "splash_screen")
    object SearchScreen: Screen(route = "search_screen")
    object FilmDetailsScreen: Screen(route = "film_detail_screen")
    object FavouritesScreen: Screen(route = "favourites_screen")
    object CastsScreen: Screen(route = "casts_recording")

    object AboutScreen: Screen(route = "about_screen")
    object TvSeriesDetailsScreen: Screen(route = "tv_series_details_screen")
    object MovieDetailsScreen: Screen(route = "movie_details_screen")
    object ProductDetailsScreen: Screen(route = "product_details_screen")

    //feature Auth
    object AuthDashboardScreen: Screen(route = "auth_dashboard_screen")
    object ForgotPasswordScreen: Screen(route = "forgot_password_screen")
    object LoginScreen: Screen(route = "login_screen")
    object RegisterScreen: Screen(route = "register_screen")
    object JoomiaSplashScreen: Screen(route = "joomia_splash_screen")

    //feature Products
    object StoreHomeScreen: Screen(route = "store_home_screen")

}