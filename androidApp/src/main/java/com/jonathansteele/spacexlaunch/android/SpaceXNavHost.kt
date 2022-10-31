package com.jonathansteele.spacexlaunch.android

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun SpaceXNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "launch"
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("launch") {
            LaunchScreen(onNavigateToDetail = {
                navController.navigate("detail/$it")
            })
        }

        composable(
            "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            LaunchDetailScreen(
                id = it.arguments?.getInt("id"),
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
