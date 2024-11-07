package com.bd.bd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.bd.bd.feature.detail.DetailDestination
import com.bd.bd.feature.detail.DetailScreen
import com.bd.bd.feature.detail.navigateToDetail
import com.bd.bd.feature.home.HomeDestination
import com.bd.bd.feature.home.HomeScreen
import com.bd.bd.ui.theme.BooksDatabaseTheme
import com.bd.ui.navigation.composableWithTransition
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BooksDatabaseTheme {
                val navController = rememberNavController()
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = HomeDestination
                    ) {
                        composableWithTransition<HomeDestination> {
                            HomeScreen(
                                navToDetail = { id, mediaType -> navController.navigateToDetail(id, mediaType) }
                            )
                        }
                        composableWithTransition<DetailDestination> {
                            DetailScreen(
                                onBackClick = { navController.popBackStack() },
                                navToDetail = { id, mediaType -> navController.navigateToDetail(id, mediaType) }
                            )
                        }
                    }
                }
            }
        }
    }
}
