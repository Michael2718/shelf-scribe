package com.example.shelf_scribe.ui

import android.content.Context
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.example.shelf_scribe.network.SearchRequestStatus
import com.example.shelf_scribe.network.VolumeRequestStatus
import com.example.shelf_scribe.ui.screens.HomeScreen
import com.example.shelf_scribe.ui.screens.search.SearchDetailsScreen
import com.example.shelf_scribe.ui.screens.search.SearchScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Search : Screen("search") {
        object Entry : Screen("entry")
        object Details : Screen("details")
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainNavHost(
    navController: NavHostController,
    context: Context,
    searchRequestStatus: SearchRequestStatus,
    volumeRequestStatus: VolumeRequestStatus,
    searchRetryAction: () -> Unit,
    getVolumeRetryAction: () -> Unit,
    onVolumeClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        modifier = modifier
    ) {
        composable(
            route = Screen.Home.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentScope.SlideDirection.End
                )
            }

        ) {
            HomeScreen()
        }
        searchGraph(
            context = context,
            searchRequestStatus = searchRequestStatus,
            volumeRequestStatus = volumeRequestStatus,
            searchRetryAction = searchRetryAction,
            getVolumeRetryAction = getVolumeRetryAction,
            onVolumeClick = onVolumeClick
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.searchGraph(
    context: Context,
    searchRequestStatus: SearchRequestStatus,
    volumeRequestStatus: VolumeRequestStatus,
    searchRetryAction: () -> Unit,
    getVolumeRetryAction: () -> Unit,
    onVolumeClick: (String) -> Unit,
) {
    navigation(
        startDestination = Screen.Search.Entry.route,
        route = Screen.Search.route
    ) {
        composable(
            route = Screen.Search.Entry.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentScope.SlideDirection.End
                )
            }
        ) {
            SearchScreen(
                searchRequestStatus = searchRequestStatus,
                context = context,
                retryAction = searchRetryAction,
                onVolumeClick = onVolumeClick
            )
        }
        composable(
            route = Screen.Search.Details.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentScope.SlideDirection.End
                )
            }
        ) {
            SearchDetailsScreen(
                volumeRequestStatus = volumeRequestStatus,
                context = context,
                retryAction = getVolumeRetryAction
            )
        }
    }
}
