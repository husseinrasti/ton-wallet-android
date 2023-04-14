package org.ton.wallet.feature.create.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import org.ton.wallet.core.navigation.NavigationEvent
import org.ton.wallet.feature.create.ui.congrats.CongratsRoute
import org.ton.wallet.feature.create.ui.start.StartRoute


const val createWalletRoute = "create_wallet_route"
private const val startScreenRoute = "start_screen_route"
private const val congratsScreenRoute = "congrats_screen_route"

fun NavController.navigateToCreateWallet(navOptions: NavOptions? = null) {
    this.navigate(createWalletRoute, navOptions)
}

fun NavGraphBuilder.createWalletGraph(
    navController: NavController,
    onClickNavigation: (NavigationEvent) -> Unit
) {
    navigation(startDestination = startScreenRoute, route = createWalletRoute) {
        composable(route = startScreenRoute) {
            StartRoute(
                onClickNavigation = {
                    if (it is RouterCreateWallet.Congratulations) {
                        navController.navigate(congratsScreenRoute)
                    } else {
                        onClickNavigation(it)
                    }
                }
            )
        }
        composable(route = congratsScreenRoute) {
            CongratsRoute(onClickNavigation = onClickNavigation)
        }
    }
}
