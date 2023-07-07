package com.sebastianvm.mango.ui.example

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.sebastianvm.mango.ui.navigation.DestinationType
import com.sebastianvm.mango.ui.navigation.NavigationRoute
import com.sebastianvm.mango.ui.navigation.screenDestination

@Suppress("UNUSED_PARAMETER")
fun NavGraphBuilder.exampleNavDestination(navController: NavController) {
    screenDestination<ExampleViewModel>(
        destination = NavigationRoute.Example,
        destinationType = DestinationType.Screen
    ) { viewModel ->
        ExampleScreen(exampleViewModel = viewModel)
    }
}
