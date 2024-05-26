package com.haxon.githubreposapp.ui.theme.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.haxon.githubreposapp.domain.model.RepoListing
import com.haxon.githubreposapp.presentation.repo_info.RepoInfoScreen
import com.haxon.githubreposapp.presentation.repo_info.WebViewScreen
import com.haxon.githubreposapp.presentation.repo_listings.RepoListingScreen

@Composable
fun AppNavigationGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            RepoListingScreen(
                onRepoClick = {
                    Log.d("***", "onRepoClick: $it")
                    navController.navigate(
                        "${Routes.REPO_INFO}?" +
                                "id=${it.id}&" +
                                "repoName=${it.repoName}&" +
                                "ownerName=${it.ownerName}&" +
                                "projectLink=${it.projectLink ?: ""}&" +
                                "ownerImageLink=${it.ownerImageLink ?: ""}&" +
                                "description=${it.description ?: ""}&" +
                                "contributorsURL=${it.contributorsURL ?: ""}"
                    )
                }
            )
        }
        composable(
            route = "${Routes.REPO_INFO}?" +
                    "id={id}&" +
                    "repoName={repoName}&" +
                    "ownerName={ownerName}&" +
                    "projectLink={projectLink}&" +
                    "ownerImageLink={ownerImageLink}&" +
                    "description={description}&" +
                    "contributorsURL={contributorsURL}",
            arguments = listOf(
                navArgument("id") {type = NavType.IntType},
                navArgument("repoName") {type = NavType.StringType},
                navArgument("ownerName") {type = NavType.StringType},
                navArgument("projectLink") {type = NavType.StringType},
                navArgument("ownerImageLink") {type = NavType.StringType},
                navArgument("description") {type = NavType.StringType},
                navArgument("contributorsURL") {type = NavType.StringType}
            )
        ) {
            val repo = RepoListing(
                id = it.arguments?.getInt("id") ?: 0,
                repoName = it.arguments?.getString("repoName") ?: "",
                ownerName = it.arguments?.getString("ownerName") ?: "",
                projectLink = it.arguments?.getString("projectLink") ?: "",
                ownerImageLink = it.arguments?.getString("ownerImageLink") ?: "",
                description = it.arguments?.getString("description") ?: "",
                contributorsURL = it.arguments?.getString("contributorsURL") ?: ""
            )
            RepoInfoScreen(
                repo = repo,
                onProjectLinkClick = {link ->
                    navController.navigate("${Routes.WEB_VIEW}?url=$link")
                }
            )
        }

        composable(
            route = "${Routes.WEB_VIEW}?url={url}",
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        ) { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url") ?: ""
            WebViewScreen(url = url)
        }
    }
}