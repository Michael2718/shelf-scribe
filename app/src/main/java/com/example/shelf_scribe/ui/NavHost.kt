package com.example.shelf_scribe.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.shelf_scribe.network.SearchRequestStatus
import com.example.shelf_scribe.network.VolumeRequestStatus
import com.example.shelf_scribe.ui.screens.HomeScreen
import com.example.shelf_scribe.ui.screens.search.SearchDetailsScreen
import com.example.shelf_scribe.ui.screens.search.SearchScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Search : Screen("search") {
        object Entry : Screen("entry")
        object Details : Screen("details")
    }
}

@Composable
fun MainNavHost(
    navController: NavHostController,
    context: Context,
    searchRequestStatus: SearchRequestStatus,
    volumeRequestStatus: VolumeRequestStatus,
    onVolumeClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen()
        }
        searchGraph(
            context = context,
            searchRequestStatus = searchRequestStatus,
            volumeRequestStatus = volumeRequestStatus,
            onVolumeClick = onVolumeClick
        )
    }
}

fun NavGraphBuilder.searchGraph(
    context: Context,
    searchRequestStatus: SearchRequestStatus,
    volumeRequestStatus: VolumeRequestStatus,
    onVolumeClick: (String) -> Unit,
) {
    navigation(
        startDestination = Screen.Search.Entry.route,
        route = Screen.Search.route
    ) {
        composable(Screen.Search.Entry.route) {
            SearchScreen(
                searchRequestStatus = searchRequestStatus,
                context = context,
                onVolumeClick = onVolumeClick
            )
        }
        composable(Screen.Search.Details.route) {
            SearchDetailsScreen(
                volumeRequestStatus = volumeRequestStatus,
                context = context
            )
        }
    }
}
