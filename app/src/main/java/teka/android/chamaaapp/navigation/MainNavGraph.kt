package teka.android.chamaaapp.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import teka.android.chamaaapp.presentation.addChamaaAccount.AddChamaaAccountScreen
import teka.android.chamaaapp.presentation.addContribution.AddContributionScreen
import teka.android.chamaaapp.presentation.addContribution.AddContributionViewModel
import teka.android.chamaaapp.presentation.addMember.AddMemberScreen
import teka.android.chamaaapp.presentation.home.HomeScreen
import teka.android.chamaaapp.presentation.members.MemberHomeScreen
import teka.android.chamaaapp.presentation.profile.presentation.account.ProfileScreen
import teka.android.chamaaapp.presentation.wallet.WalletHomeScreen
import teka.android.chamaaapp.ui.animations.scaleIntoContainer
import teka.android.chamaaapp.ui.animations.scaleOutOfContainer
import java.lang.reflect.Member


@Composable
fun MainNavGraph(
    navController: NavHostController = rememberNavController(),
) {

    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route,
        route = MAIN_GRAPH_ROUTE
    ) {

        composable(
            route = Screen.HomeScreen.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }
        ){
            HomeScreen(navController = navController)
        }

        composable(
            route = Screen.WalletScreen.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }
        ){
            WalletHomeScreen(navController = navController)
        }

        composable(
            route = Screen.MembersScreen.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }
        ){
            MemberHomeScreen(navController = navController)
        }

        composable(
            route = Screen.AddMemberScreen.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }
        ){
            AddMemberScreen(navController = navController)
        }

        composable(
            route = Screen.AddContributionScreen.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }
        ){
            AddContributionScreen(navController = navController )
        }

        composable(
            route = Screen.ProfileScreen.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }
        ){
            ProfileScreen(
                navigator = navController
            )
        }

        composable(
            route = Screen.AddChamaaAccountScreen.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }
        ){
            AddChamaaAccountScreen(
                navController = navController
            )
        }

        composable(
            route = "${Screen.TvSeriesDetailsScreen.route}?filmId={filmId}",
            arguments = listOf(
                navArgument(name = "filmId"){
                    type = NavType.IntType
                }
            )
        ){backstackEntry ->
//            backstackEntry.arguments?.getInt("filmId")?.let { TvSeriesDetailsScreen(
//                filmId = it,
//                navigator = navController
//            ) }
        }

        composable(
            route = "${Screen.MovieDetailsScreen.route}?filmId={filmId}",
            arguments = listOf(
                navArgument(name = "filmId"){
                    type = NavType.IntType
                }
            )
        ){backstackEntry ->
//            backstackEntry.arguments?.getInt("filmId")?.let { MovieDetailsScreen(
//                filmId = it,
//                navigator = navController
//            ) }
        }


        composable(
            route = Screen.CastsScreen.route
        ){
//            val result =
//                navController.previousBackStackEntry?.savedStateHandle?.get<CreditsResponse>("creditsResponse")
//            if (result != null) {
//                CastsScreen(creditsResponse = result, navigator = navController)
//            }
        }

        composable(
            route = Screen.ProductDetailsScreen.route
        ){
//            val result =
//                navController.previousBackStackEntry?.savedStateHandle?.get<Product>("product")
//            if (result != null) {
//                ProductDetailsScreen(product = result, navigator = navController)
//            }
        }



    }
}