package com.bd.bd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bd.bd.feature.detail.DetailDestination
import com.bd.bd.feature.detail.DetailScreen
import com.bd.bd.feature.home.HomeDestination
import com.bd.bd.feature.home.HomeScreen
import com.bd.bd.ui.theme.BooksDatabaseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BooksDatabaseTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = HomeDestination
                ) {
                    composable<HomeDestination> {
                        HomeScreen(
                            navToDetail = { navController.navigate(DetailDestination(it)) }
                        )
                    }
                    composable<DetailDestination> {
                        DetailScreen()
                    }
                }
            }
        }
    }
}
