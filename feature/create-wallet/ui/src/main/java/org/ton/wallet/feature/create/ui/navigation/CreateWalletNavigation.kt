package org.ton.wallet.feature.create.ui.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import org.ton.wallet.core.navigation.NavigateUp
import org.ton.wallet.core.navigation.NavigationEvent
import org.ton.wallet.feature.create.ui.congrats.CongratsRoute
import org.ton.wallet.feature.create.ui.phrase.recovery.RecoveryPhraseRoute
import org.ton.wallet.feature.create.ui.phrase.test.TestPhraseRoute
import org.ton.wallet.feature.create.ui.start.StartRoute

const val createWalletRoute = "create_wallet_route"
private const val startScreenRoute = "start_screen_route"
private const val congratsScreenRoute = "congrats_screen_route"
private const val generatePhraseScreenRoute = "generate_phrase_screen_route"
private const val testPhraseScreenRoute = "test_phrase_screen_route"

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
            CongratsRoute(onClickNavigation = { event ->
                if (event is RouterCreateWallet.GeneratePhrase) {
                    navController.navigate(generatePhraseScreenRoute)
                } else {
                    onClickNavigation(event)
                }
            })
        }
        composable(route = generatePhraseScreenRoute) {
            RecoveryPhraseRoute(onClickNavigation = { event ->
                when (event) {
                    is NavigateUp -> {
                        navController.popBackStack()
                    }
                    is RouterCreateWallet.TestPhrase -> {
                        navController.navigate(testPhraseScreenRoute)
                    }
                    else -> {
                        onClickNavigation(event)
                    }
                }
            })
        }
        composable(route = testPhraseScreenRoute) {
            TestPhraseRoute(onClickNavigation = onClickNavigation)
        }
    }
}
