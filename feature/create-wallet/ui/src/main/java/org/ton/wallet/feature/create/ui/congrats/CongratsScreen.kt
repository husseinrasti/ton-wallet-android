package org.ton.wallet.feature.create.ui.congrats

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import org.ton.wallet.core.navigation.NavigateUp
import org.ton.wallet.core.navigation.NavigationEvent
import org.ton.wallet.core.ui.component.TonButton
import org.ton.wallet.core.ui.component.TonLottieAnimation
import org.ton.wallet.core.ui.component.TonTopAppBar
import org.ton.wallet.core.ui.theme.TonWalletTheme
import org.ton.wallet.feature.create.ui.R
import org.ton.wallet.feature.create.ui.navigation.RouterCreateWallet


@Composable
internal fun CongratsRoute(
    onClickNavigation: (NavigationEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    CongratsScaffoldScreen(
        onClickNavigation = onClickNavigation,
        modifier = modifier
    )
}

@Composable
private fun CongratsScaffoldScreen(
    onClickNavigation: (NavigationEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TonTopAppBar(
                onClickNavigation = { onClickNavigation(NavigateUp) },
                elevation = 0.dp
            )
        },
        content = {
            CongratsScreen(
                onClickNavigation = onClickNavigation,
                modifier = modifier.padding(it)
            )
        }
    )
}

@Composable
private fun CongratsScreen(
    onClickNavigation: (NavigationEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize(),
        ) {
            val (container, button) = createRefs()

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.constrainAs(container) {
                    top.linkTo(parent.top, margin = 32.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    bottom.linkTo(button.top, margin = 16.dp)
                }
            ) {
                TonLottieAnimation(
                    lottieCompositionSpec = LottieCompositionSpec.Asset("anim/congratulations.json"),
                    modifier = Modifier.size(128.dp),
                    restartOnPlay = true,
                    iterations = LottieConstants.IterateForever,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    modifier = Modifier
                        .padding(PaddingValues(horizontal = 16.dp)),
                    text = stringResource(id = R.string.title_congratulations),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.secondary,
                    style = MaterialTheme.typography.body1.copy(fontSize = 24.sp),
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    modifier = Modifier
                        .padding(PaddingValues(horizontal = 16.dp)),
                    text = stringResource(id = R.string.desc_congratulations),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.caption,
                )
            }

            TonButton(
                text = stringResource(id = R.string.btn_proceed),
                modifier = Modifier.constrainAs(button) {
                    top.linkTo(container.bottom, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    bottom.linkTo(parent.bottom, margin = 32.dp)
                },
                onClick = { onClickNavigation(RouterCreateWallet.GeneratePhrase) }
            )

        }
    }
}


@Preview(heightDp = 800, widthDp = 480)
@Composable
private fun CongratsScreenPreview() {
    TonWalletTheme {
        CongratsScaffoldScreen(onClickNavigation = {})
    }
}