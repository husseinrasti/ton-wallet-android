package org.ton.wallet.feature.create.ui.start

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import kotlinx.coroutines.flow.collectLatest
import org.ton.wallet.core.navigation.NavigateImportWallet
import org.ton.wallet.core.navigation.NavigationEvent
import org.ton.wallet.core.ui.component.TonButton
import org.ton.wallet.core.ui.component.TonLottieAnimation
import org.ton.wallet.core.ui.theme.TonWalletTheme
import org.ton.wallet.feature.create.ui.R
import org.ton.wallet.feature.create.ui.navigation.RouterCreateWallet


@Composable
internal fun StartRoute(
    onClickNavigation: (NavigationEvent) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: StartViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    context: Context = LocalContext.current,
) {

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        ProgressDialog { showDialog = false }
    }

    LaunchedEffect(snackbarHostState) {
        viewModel.uiState.collectLatest { state ->
            showDialog = state is StartUiState.Loading
            when (state) {
                StartUiState.Error -> {
                    snackbarHostState.showSnackbar(
                        message = context.resources.getString(R.string.msg_error)
                    )
                }

                StartUiState.Success -> {
                    onClickNavigation(RouterCreateWallet.Congratulations)
                }

                else -> {}
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        modifier = modifier
    ) {
        StartScreen(
            onClickNavigation = onClickNavigation,
            modifier = Modifier.padding(it),
            onCreateWallet = { viewModel.generatePhrases() }
        )
    }
}

@Composable
private fun StartScreen(
    onClickNavigation: (NavigationEvent) -> Unit,
    onCreateWallet: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize(),
        ) {
            val (tonInfo, tonCreate) = createRefs()

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.constrainAs(tonInfo) {
                    top.linkTo(parent.top, margin = 32.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    bottom.linkTo(tonCreate.top, margin = 16.dp)
                }
            ) {
                TonLottieAnimation(
                    lottieCompositionSpec = LottieCompositionSpec.Asset("anim/start.json"),
                    modifier = Modifier.size(128.dp),
                    restartOnPlay = true,
                    iterations = LottieConstants.IterateForever,
                )

                Text(
                    modifier = Modifier
                        .padding(
                            PaddingValues(
                                horizontal = 16.dp,
                                vertical = 8.dp
                            )
                        ),
                    text = stringResource(id = R.string.title_ton_wallet),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.secondary,
                    style = MaterialTheme.typography.body1.copy(fontSize = 24.sp),
                )

                Text(
                    modifier = Modifier
                        .padding(PaddingValues(horizontal = 16.dp)),
                    text = stringResource(id = R.string.desc_ton_wallet),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.caption,
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.constrainAs(tonCreate) {
                    top.linkTo(tonInfo.bottom)
                    end.linkTo(parent.end, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    bottom.linkTo(parent.bottom)
                }
            ) {
                TonButton(
                    text = stringResource(id = R.string.btn_create_my_wallet),
                    modifier = Modifier.padding(16.dp),
                    onClick = onCreateWallet
                )

                TextButton(onClick = {
                    onClickNavigation(NavigateImportWallet)
                }) {
                    Text(
                        text = stringResource(id = R.string.btn_import_existing_wallet),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colors.secondaryVariant,
                        style = MaterialTheme.typography.button,
                    )
                }
            }

        }
    }
}


@Composable
private fun ProgressDialog(onDismissRequest: () -> Unit) {
    Dialog(
        onDismissRequest = onDismissRequest,
        DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(72.dp)
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}

@Preview(heightDp = 800, widthDp = 480)
@Composable
private fun StartScreenPreview() {
    TonWalletTheme {
        StartScreen(
            onClickNavigation = {},
            onCreateWallet = {}
        )
    }
}