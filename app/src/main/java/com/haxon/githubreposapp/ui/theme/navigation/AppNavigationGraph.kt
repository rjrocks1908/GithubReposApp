package com.haxon.githubreposapp.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.haxon.githubreposapp.presentation.repo_listings.RepoListingScreen

@Composable
fun AppNavigationGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            RepoListingScreen()
        }
        composable(Routes.REPO_INFO) {

        }
    }
}