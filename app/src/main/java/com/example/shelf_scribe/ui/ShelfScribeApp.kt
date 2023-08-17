package com.example.shelf_scribe.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.shelf_scribe.R
import com.example.shelf_scribe.ui.components.HomeScreenTopAppBar
import com.example.shelf_scribe.ui.components.SearchTopBar


@Composable
fun ShelfScribeApp(
    modifier: Modifier = Modifier,
    viewModel: ShelfScribeViewModel = viewModel(factory = ShelfScribeViewModel.Factory),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
//    val currentScreen = Screen.valueOf(
//        backStackEntry?.destination?.route ?: ShelfScribeScreen.Start.name
//    )
    val currentDestination = backStackEntry?.destination
    val currentRoute = currentDestination?.route ?: Screen.Home.route

    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current

    val navigationItemContentList = listOf(
        NavigationItemContent(
            route = Screen.Home.route,
            icon = Icons.Filled.Home,
            label = stringResource(R.string.home)
        ),
        NavigationItemContent(
            route = Screen.Search.route,
            icon = Icons.Filled.Search,
            label = stringResource(R.string.search)
        ),
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            when (currentRoute) {
                Screen.Home.route -> HomeScreenTopAppBar()
                Screen.Search.route -> SearchTopBar(
                    query = uiState.query,
                    onQueryChange = { viewModel.updateQuery(it) },
                    onSearch = { /*TODO: navigate to SearchResults*/ },
                    isSearching = uiState.isSearching,
                    onActiveChange = { /*TODO: ???*/ }
                )
            }
        },
        bottomBar = {
            BottomNavigationBar(
                currentDestination = currentDestination,
                onTabPressed = {
                    navController.navigate(it) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                navigationItemContentList = navigationItemContentList,
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) { innerPadding ->
        MainNavHost(
            navController = navController,
            context = context,
            searchRequestStatus = uiState.searchRequestStatus,
            modifier = modifier
                .padding(innerPadding)
        )
    }
}

@Composable
private fun BottomNavigationBar(
//    currentTab: TabType,
    currentDestination: NavDestination?,
    onTabPressed: ((String) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        for (navItem in navigationItemContentList) {
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == navItem.route } == true,
                onClick = { onTabPressed(navItem.route) },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.label
                    )
                },
                label = {
                    Text(
                        text = navItem.label,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    }
}

private data class NavigationItemContent(
    val route: String,
    val icon: ImageVector,
    val label: String
)
