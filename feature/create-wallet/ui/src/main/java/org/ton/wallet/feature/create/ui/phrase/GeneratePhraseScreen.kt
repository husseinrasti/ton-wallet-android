package org.ton.wallet.feature.create.ui.phrase

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieCompositionSpec
import org.ton.wallet.core.navigation.NavigateUp
import org.ton.wallet.core.navigation.NavigationEvent
import org.ton.wallet.core.ui.component.TonButton
import org.ton.wallet.core.ui.component.TonLottieAnimation
import org.ton.wallet.core.ui.component.TonTopAppBar
import org.ton.wallet.core.ui.theme.TonWalletTheme
import org.ton.wallet.feature.create.ui.R
import org.ton.wallet.feature.create.ui.navigation.RouterCreateWallet


private const val REQUIRE_MILLISECOND_WRITE_PHRASE = 60000
private const val DEFAULT_COLUMN_COUNT = 2
private const val TITLE_FONT_SCALE_STARE = 1f
private const val TITLE_FONT_SCALE_END = 0.66f

private val headerHeight = 250.dp
private val toolbarHeight = 56.dp

private val paddingMedium = 16.dp

private val titlePaddingStart = 16.dp
private val titlePaddingEnd = 72.dp


private fun isTimeDiffOne(startTime: Long, currentTimeMillis: Long): Boolean {
    return (currentTimeMillis - startTime) < REQUIRE_MILLISECOND_WRITE_PHRASE
}

@Composable
fun GeneratePhraseRoute(
    onClickNavigation: (NavigationEvent) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PhraseViewModel = hiltViewModel(),
) {

    var showAlert by remember { mutableStateOf(false) }
    val startTime by rememberSaveable { mutableStateOf(System.currentTimeMillis()) }
    var doubleClickCounter by remember { mutableStateOf(0) }

    if (showAlert) {
        PhraseAlertDialog(
            doubleClickCounter = doubleClickCounter,
            onClickSkip = {
                onClickNavigation(RouterCreateWallet.TestPhrase)
            },
            onClickOK = {
                showAlert = false
            },
            onDismissRequest = {
                showAlert = false
            }
        )
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle(
        minActiveState = Lifecycle.State.RESUMED
    )

    GeneratePhraseScreen(
        onClickNavigation = { event ->
            if (event is RouterCreateWallet.TestPhrase
                && isTimeDiffOne(startTime, System.currentTimeMillis())
            ) {
                doubleClickCounter++
                showAlert = true
            } else {
                onClickNavigation(event)
            }
        },
        modifier = modifier,
        uiState = uiState,
    )
}

@Composable
private fun GeneratePhraseScreen(
    onClickNavigation: (NavigationEvent) -> Unit,
    modifier: Modifier = Modifier,
    uiState: PhraseUiState,
) {
    val scroll: ScrollState = rememberScrollState(0)

    val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }

    when (uiState) {
        is PhraseUiState.Success -> {
            Box(modifier = modifier.fillMaxSize()) {
                Header(
                    scroll = scroll,
                    headerHeightPx = headerHeightPx,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(headerHeight)
                )
                Body(
                    scroll = scroll,
                    wordList = uiState.wordList,
                    onClick = { onClickNavigation(RouterCreateWallet.TestPhrase) },
                )
                TonTopAppBar(
                    onClickNavigation = { onClickNavigation(NavigateUp) },
                    elevation = 0.dp
                )
                Title(scroll = scroll)
            }
        }
        PhraseUiState.Loading -> {}
        PhraseUiState.Error -> {}
    }
}

@Composable
private fun Header(
    scroll: ScrollState,
    headerHeightPx: Float,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .graphicsLayer {
                translationY = -scroll.value.toFloat() / 2f // Parallax effect
                alpha = (-1f / headerHeightPx) * scroll.value + 1
            },
        contentAlignment = Alignment.Center,
    ) {
        TonLottieAnimation(
            lottieCompositionSpec = LottieCompositionSpec.Asset("anim/recovery_phrase.json"),
            modifier = Modifier.size(128.dp)
        )
    }
}

@Composable
private fun Body(
    scroll: ScrollState,
    modifier: Modifier = Modifier,
    wordList: List<String>,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(scroll)
    ) {
        Spacer(Modifier.height(headerHeight))
        Text(
            text = stringResource(R.string.desc_your_recovery_phrase),
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(32.dp))

        GridMnemonic(data = wordList)

        Spacer(Modifier.height(32.dp))
        TonButton(
            text = stringResource(id = R.string.btn_done),
            onClick = onClick,
            contentPadding = PaddingValues(
                horizontal = 96.dp,
                vertical = 8.dp
            ),
        )
        Spacer(Modifier.height(72.dp))
    }
}

@Composable
private fun GridMnemonic(
    data: List<String>,
    modifier: Modifier = Modifier,
    columnCount: Int = DEFAULT_COLUMN_COUNT,
) {
    val size = data.size
    for (rowIndex in data.indices) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            for (columnIndex in 0 until columnCount) {
                val itemIndex = rowIndex * columnCount + columnIndex
                if (itemIndex < size) {
                    Box(
                        modifier = Modifier.weight(1F, fill = true),
                        propagateMinConstraints = true,
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "${itemIndex + 1}. ",
                                style = MaterialTheme.typography.body2,
                                color = MaterialTheme.colors.primaryVariant,
                                textAlign = TextAlign.End,
                                modifier = Modifier.weight(0.2F, fill = true),
                            )
                            Text(
                                text = data[itemIndex],
                                style = MaterialTheme.typography.body1,
                                color = MaterialTheme.colors.secondary,
                                modifier = Modifier
                                    .weight(1F, fill = true)
                                    .padding(horizontal = 8.dp),
                                textAlign = TextAlign.Start,
                            )
                        }
                    }
                } else {
                    Spacer(Modifier.weight(1F, fill = true))
                }
            }
        }
    }
}

@Composable
private fun Title(
    scroll: ScrollState,
    modifier: Modifier = Modifier
) {
    var titleHeightPx by remember { mutableStateOf(0f) }
    var titleWidthPx by remember { mutableStateOf(0f) }

    Text(
        text = stringResource(id = R.string.title_your_recovery_phrase),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.secondary,
        style = MaterialTheme.typography.body1.copy(fontSize = 24.sp),
        modifier = modifier
            .fillMaxWidth()
            .padding(end = 32.dp)
            .graphicsLayer {
                val collapseRange: Float = (headerHeight.toPx() - toolbarHeight.toPx())
                val collapseFraction: Float = (scroll.value / collapseRange).coerceIn(0f, 1f)

                val scaleXY = lerp(
                    TITLE_FONT_SCALE_STARE.dp,
                    TITLE_FONT_SCALE_END.dp,
                    collapseFraction
                )

                val titleExtraStartPadding = titleWidthPx.toDp() * (1 - scaleXY.value) / 2f

                val titleYFirstInterpolatedPoint = lerp(
                    headerHeight - titleHeightPx.toDp() - paddingMedium,
                    headerHeight / 2,
                    collapseFraction
                )

                val titleXFirstInterpolatedPoint = lerp(
                    titlePaddingStart,
                    (titlePaddingEnd - titleExtraStartPadding) * 5 / 4,
                    collapseFraction
                )

                val titleYSecondInterpolatedPoint = lerp(
                    headerHeight / 2,
                    toolbarHeight / 2 - titleHeightPx.toDp() / 2,
                    collapseFraction
                )

                val titleXSecondInterpolatedPoint = lerp(
                    (titlePaddingEnd - titleExtraStartPadding) * 5 / 4,
                    titlePaddingEnd - titleExtraStartPadding,
                    collapseFraction
                )

                val titleY = lerp(
                    titleYFirstInterpolatedPoint,
                    titleYSecondInterpolatedPoint,
                    collapseFraction
                )

                val titleX = lerp(
                    titleXFirstInterpolatedPoint,
                    titleXSecondInterpolatedPoint,
                    collapseFraction
                )

                translationY = titleY.toPx()
                translationX = titleX.toPx()
            }
            .onGloballyPositioned {
                titleHeightPx = it.size.height.toFloat()
                titleWidthPx = it.size.width.toFloat()
            }
    )
}

@Composable
private fun PhraseAlertDialog(
    doubleClickCounter: Int,
    onDismissRequest: () -> Unit,
    onClickSkip: () -> Unit,
    onClickOK: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        buttons = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                if (doubleClickCounter >= 2) {
                    TextButton(onClick = onClickSkip) {
                        Text(
                            text = stringResource(id = R.string.btn_skip),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp),
                            color = MaterialTheme.colors.secondaryVariant,
                            style = MaterialTheme.typography.button,
                        )
                    }
                }
                TextButton(onClick = onClickOK) {
                    Text(
                        text = stringResource(id = R.string.btn_ok_sorry),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colors.secondaryVariant,
                        style = MaterialTheme.typography.button,
                    )
                }
            }
        },
        title = {
            Text(
                text = stringResource(id = R.string.title_sure_done),
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.body1.copy(fontSize = 20.sp),
            )
        },
        text = {
            Text(
                text = stringResource(id = R.string.msg_enough_time_write_words),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSurface,
            )
        },
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface,
    )
}

@Preview
@Composable
private fun GeneratePhrasePreview() {
    TonWalletTheme {
        GeneratePhraseScreen(
            onClickNavigation = {},
            uiState = PhraseUiState.Success(fakeWordList)
        )
    }
}

@Preview
@Composable
private fun PhraseAlertDialogPreview() {
    TonWalletTheme {
        PhraseAlertDialog(
            doubleClickCounter = 2,
            onClickSkip = {},
            onClickOK = {},
            onDismissRequest = {}
        )
    }
}

private val fakeWordList = listOf(
    "abandon",
    "ability",
    "able",
    "about",
    "above",
    "absent",
    "absorb",
    "abstract",
    "absurd",
    "abuse",
    "access",
    "accident",
    "account",
    "accuse",
    "achieve",
    "acid",
    "acoustic",
    "acquire",
    "across",
    "act",
    "action",
    "actor",
    "actress",
    "actual",
)