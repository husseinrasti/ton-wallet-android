package org.ton.wallet.feature.create.ui.start

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
import org.ton.wallet.core.navigation.NavigateCreateWallet
import org.ton.wallet.core.navigation.NavigateImportWallet
import org.ton.wallet.core.navigation.NavigationOnClickEvent
import org.ton.wallet.core.ui.component.TonButton
import org.ton.wallet.core.ui.component.TonLottieAnimation
import org.ton.wallet.core.ui.theme.TonWalletTheme
import org.ton.wallet.feature.create.ui.R


@Composable
internal fun StartRoute(
    onClickNavigation: (NavigationOnClickEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    StartScreen(
        onClickNavigation = onClickNavigation,
        modifier = modifier
    )
}

@Composable
internal fun StartScreen(
    onClickNavigation: (NavigationOnClickEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize(),
        ) {
            val (container, createWallet, importWallet) = createRefs()

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.constrainAs(container) {
                    top.linkTo(parent.top, margin = 32.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    bottom.linkTo(createWallet.top, margin = 16.dp)
                }
            ) {
                TonLottieAnimation(
                    lottieCompositionSpec = LottieCompositionSpec.Asset("anim/start.json"),
                    modifier = Modifier.size(128.dp)
                )

                Text(
                    modifier = Modifier
                        .padding(
                            PaddingValues(
                                horizontal = 16.dp,
                                vertical = 8.dp
                            )
                        ),
                    text = stringResource(id = R.string.start_title_ton_wallet),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.body1.copy(fontSize = 24.sp),
                )

                Text(
                    modifier = Modifier
                        .padding(PaddingValues(horizontal = 16.dp)),
                    text = stringResource(id = R.string.start_desc_ton_wallet),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.caption,
                )
            }

            TonButton(
                text = stringResource(id = R.string.btn_create_my_wallet),
                modifier = Modifier.constrainAs(createWallet) {
                    bottom.linkTo(importWallet.top, margin = 8.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                },
                onClick = { onClickNavigation(NavigateCreateWallet) }
            )

            Text(
                text = stringResource(id = R.string.btn_import_existing_wallet),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .constrainAs(importWallet) {
                        bottom.linkTo(parent.bottom, margin = 32.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                    }
                    .clickable { onClickNavigation(NavigateImportWallet) },
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.button,
            )

        }
    }
}


@Preview(heightDp = 800, widthDp = 480)
@Composable
fun StartScreenPreview() {
    TonWalletTheme {
        StartScreen(onClickNavigation = {})
    }
}