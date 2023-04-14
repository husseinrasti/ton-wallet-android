package org.ton.wallet.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.ton.wallet.feature.create.ui.navigation.createWalletGraph
import org.ton.wallet.feature.create.ui.navigation.createWalletRoute

@Composable
fun TonNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = createWalletRoute,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        createWalletGraph(onClickNavigation = {})
    }
}
