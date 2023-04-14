package org.ton.wallet.app.navigation

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.ton.wallet.core.navigation.NavigateUp
import org.ton.wallet.core.navigation.NavigationEvent
import org.ton.wallet.core.navigation.navigationSaver
import org.ton.wallet.feature.create.ui.navigation.createWalletGraph
import org.ton.wallet.feature.create.ui.navigation.createWalletRoute

@Composable
fun TonNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = createWalletRoute,
) {

    var navigationEvent: NavigationEvent by remember {
        mutableStateOf(NavigationEvent.Idle)
    }

    when (navigationEvent) {
        is NavigateUp -> navController.navigateUp()
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        createWalletGraph(
            navController = navController,
            onClickNavigation = { navigationEvent = it }
        )
    }
}
