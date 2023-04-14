package org.ton.wallet.feature.create.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import org.ton.wallet.core.navigation.NavigationOnClickEvent
import org.ton.wallet.feature.create.ui.start.StartRoute


const val createWalletRoute = "create_wallet_route"
private const val startScreenRoute = "start_screen_route"

fun NavController.navigateToCreateWallet(navOptions: NavOptions? = null) {
    this.navigate(createWalletRoute, navOptions)
}

fun NavGraphBuilder.createWalletGraph(onClickNavigation: (NavigationOnClickEvent) -> Unit) {
    navigation(startDestination = startScreenRoute, route = createWalletRoute) {
        composable(route = startScreenRoute) {
            StartRoute(onClickNavigation)
        }
    }
}
