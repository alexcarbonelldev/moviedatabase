package com.bd.bd.feature.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bd.bd.R
import com.bd.bd.common.nav.mediaTypeArgNavType
import com.bd.bd.feature.detail.DetailDestination
import com.bd.bd.feature.detail.DetailScreen
import com.bd.bd.feature.detail.navigateToMovieDetail
import com.bd.bd.feature.detail.navigateToTvShowDetail
import com.bd.bd.feature.home.HomeScreen
import com.bd.bd.feature.search.SearchScreen
import com.bd.domain.model.ContentType
import com.bd.ui.design_system.Icons
import com.bd.ui.navigation.composableWithTransition
import kotlin.reflect.typeOf

private val homeTab = BottomBarItem(
    title = R.string.home,
    selectedIcon = Icons.Home,
    unselectedIcon = Icons.Home,
    route = "home",
)
private val searchTab = BottomBarItem(
    title = R.string.search,
    selectedIcon = Icons.Search,
    unselectedIcon = Icons.Search,
    route = "search",
)

@Composable
fun MainScreen() {

    val tabBarItems = listOf(homeTab, searchTab)

    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBarUiComponent(tabBarItems, navController) }
    ) { paddingValues ->
        Box(Modifier.padding(paddingValues)) {
            NavHost(
                navController = navController,
                startDestination = homeTab.route
            ) {
                composable(route = homeTab.route) {
                    HomeScreen(
                        navToMovieDetail = { id -> navController.navigateToMovieDetail(id) },
                        navToTvShowDetail = { id -> navController.navigateToTvShowDetail(id) },
                    )
                }
                composable(route = searchTab.route) {
                    SearchScreen(
                        navToMovieDetail = { id -> navController.navigateToMovieDetail(id) },
                        navToTvShowDetail = { id -> navController.navigateToTvShowDetail(id) },
                    )
                }

                composableWithTransition<DetailDestination>(
                    typeMap = mapOf(typeOf<ContentType.Media>() to mediaTypeArgNavType)
                ) {
                    DetailScreen(
                        onBackClick = { navController.popBackStack() },
                        navToMovieDetail = { id -> navController.navigateToMovieDetail(id) },
                        navToTvShowDetail = { id -> navController.navigateToTvShowDetail(id) },
                    )
                }
            }
        }
    }
}

data class BottomBarItem(
    @StringRes val title: Int,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
    val route: String,
)

@Composable
fun BottomBarUiComponent(
    bottomBarItems: List<BottomBarItem>,
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        bottomBarItems.forEach { tabBarItem ->
            val isSelected = currentRoute == tabBarItem.route
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(tabBarItem.route) {
                        popUpTo(homeTab.route) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                icon = {
                    TabBarIconView(
                        isSelected = isSelected,
                        selectedIcon = tabBarItem.selectedIcon,
                        unselectedIcon = tabBarItem.unselectedIcon,
                        title = stringResource(tabBarItem.title),
                    )
                },
                label = { Text(stringResource(tabBarItem.title)) })
        }
    }
}

@Composable
private fun TabBarIconView(
    isSelected: Boolean,
    selectedIcon: Int,
    unselectedIcon: Int,
    title: String,
) {
    val icon = if (isSelected) selectedIcon else unselectedIcon
    Icon(
        painter = painterResource(icon),
        contentDescription = title
    )
}
