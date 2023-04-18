package org.ton.wallet.feature.create.ui.phrase

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.ton.wallet.core.navigation.NavigationEvent
import org.ton.wallet.core.ui.component.TonTopBar


@Composable
fun GeneratePhraseRoute(
    onClickNavigation: (NavigationEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    GeneratePhraseScaffoldScreen(
        onClickNavigation = onClickNavigation,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneratePhraseScaffoldScreen(
    onClickNavigation: (NavigationEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {

                },

                )
        },
    ) {
        GeneratePhraseScreen(
            onClickNavigation = onClickNavigation,
            modifier = modifier.padding(it)
        )
    }
}

@Composable
fun GeneratePhraseScreen(
    onClickNavigation: (NavigationEvent) -> Unit,
    modifier: Modifier = Modifier,
) {

}