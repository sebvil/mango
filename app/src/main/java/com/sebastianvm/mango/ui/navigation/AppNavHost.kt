package com.sebastianvm.mango.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navigation
import com.sebastianvm.mango.ui.example.exampleNavDestination

@Composable
fun AppNavHost(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = NavigationRoute.EXAMPLE.name,
    ) {
        mainGraph(navController)
    }
}

fun NavGraphBuilder.mainGraph(navController: NavController) {
    navigation(
        startDestination = NavigationRoute.EXAMPLE.name,
        route = NavigationRoute.EXAMPLE.name
    ) {
        exampleNavDestination(navController)
    }
}