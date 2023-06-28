package com.sebastianvm.mango.ui.navigation

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

private const val ARGS = "ARGS"

enum class NavigationRoute(val hasArgs: Boolean) {
    EXAMPLE(hasArgs = false)
}


interface NavigationArguments : Parcelable

sealed class NavigationDestination(
    val navigationRoute: NavigationRoute,
    open val arguments: NavigationArguments?,
) {
    object Example :
        NavigationDestination(navigationRoute = NavigationRoute.EXAMPLE, arguments = null)
}


private val module = SerializersModule {
    polymorphic(NavigationArguments::class) {

    }
}

private val json = Json { serializersModule = module }

fun NavController.navigateTo(
    destination: NavigationDestination, builder: NavOptionsBuilder.() -> Unit = {}
) {
    val navRoute = if (destination.navigationRoute.hasArgs) {
        val encodedArgs = Uri.encode(json.encodeToString(destination.arguments))
        "${destination.navigationRoute.name}/args=$encodedArgs"
    } else {
        destination.navigationRoute.name
    }
    navigate(navRoute, builder)
}


fun getArgumentsType(): NavType<NavigationArguments> =
    object : NavType<NavigationArguments>(false) {
        override fun put(bundle: Bundle, key: String, value: NavigationArguments) {
            bundle.putParcelable(key, value)
        }

        override fun get(bundle: Bundle, key: String): NavigationArguments {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable(key, NavigationArguments::class.java)!!
            } else {
                @Suppress("deprecation")
                bundle.getParcelable(key)!!
            }
        }

        override fun parseValue(value: String): NavigationArguments {
            return json.decodeFromString(value)
        }
    }

enum class DestinationType { Screen, BottomSheet }

@OptIn(ExperimentalMaterialNavigationApi::class)
internal inline fun <reified VM : ViewModel> NavGraphBuilder.screenDestination(
    destination: NavigationRoute,
    destinationType: DestinationType,
    crossinline screen: @Composable (VM) -> Unit
) {
    val route = if (destination.hasArgs) {
        "${destination.name}/args={$ARGS}"
    } else {
        destination.name
    }
    val args = if (destination.hasArgs) listOf(navArgument(ARGS) {
        type = getArgumentsType()
    }) else listOf()

    when (destinationType) {
        DestinationType.Screen -> composable(route = route, arguments = args) {
            val screenViewModel = hiltViewModel<VM>()
            screen(screenViewModel)
        }

        DestinationType.BottomSheet -> bottomSheet(route = route, arguments = args) {
            val screenViewModel = hiltViewModel<VM>()
            screen(screenViewModel)
        }
    }
}
