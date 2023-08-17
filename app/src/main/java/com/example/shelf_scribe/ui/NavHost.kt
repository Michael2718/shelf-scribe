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
import com.example.shelf_scribe.ui.screens.HomeScreen
import com.example.shelf_scribe.ui.screens.search.SearchResultsScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Search : Screen("search") {
        object Entry : Screen("entry")
        object Results : Screen("results")
    }
    object Details : Screen("details")
}

@Composable
fun MainNavHost(
    navController: NavHostController,
    context: Context,
    searchRequestStatus: SearchRequestStatus,
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
            navController = navController,
            context = context,
            searchRequestStatus = searchRequestStatus
        )
    }
}

fun NavGraphBuilder.searchGraph(
    navController: NavHostController,
    context: Context,
    searchRequestStatus: SearchRequestStatus
) {
    navigation(
        startDestination = Screen.Search.Entry.route,
        route = Screen.Search.route
    ) {
        composable(Screen.Search.Entry.route) {
            // Search Bar
        }
        composable(Screen.Search.Results.route) {
            SearchResultsScreen(
                searchRequestStatus = searchRequestStatus,
                context = context,
            )
        }
    }
}
