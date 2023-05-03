package org.ton.wallet.app.navigation

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.ton.wallet.core.navigation.NavigateUp
import org.ton.wallet.core.navigation.NavigationEvent
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

    val dispatcher = LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher

    when (navigationEvent) {
        is NavigateUp -> dispatcher.onBackPressed()
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
