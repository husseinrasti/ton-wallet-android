package org.ton.wallet.feature.create.ui.phrase.test

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieCompositionSpec
import org.ton.wallet.core.navigation.NavigateUp
import org.ton.wallet.core.navigation.NavigationEvent
import org.ton.wallet.core.ui.component.TonButton
import org.ton.wallet.core.ui.component.TonLottieAnimation
import org.ton.wallet.core.ui.component.TonSurface
import org.ton.wallet.core.ui.component.TonTextField
import org.ton.wallet.core.ui.component.TonTopAppBar
import org.ton.wallet.core.ui.theme.TonWalletTheme
import org.ton.wallet.feature.create.ui.R
import org.ton.wallet.feature.create.ui.navigation.RouterCreateWallet
import org.ton.wallet.feature.create.ui.phrase.model.EditTextState

@Composable
internal fun TestPhraseRoute(
    modifier: Modifier = Modifier,
    onClickNavigation: (NavigationEvent) -> Unit,
    viewModel: TestPhraseViewModel = hiltViewModel(),
) {
    val randomNumbers by viewModel.randomNumbers.collectAsStateWithLifecycle(
        minActiveState = Lifecycle.State.RESUMED
    )

    if (randomNumbers.size == COUNT_PHRASE_TEST) {
        TestPhraseScaffoldScreen(
            onClickNavigation = onClickNavigation,
            modifier = modifier,
            randomNumbers = randomNumbers,
            editTextStates = viewModel.editTextStates,
            updateEditTextState = viewModel::updateEditTextState
        )
    } else {
        onClickNavigation(NavigateUp)
    }
}


@Composable
private fun TestPhraseScaffoldScreen(
    modifier: Modifier = Modifier,
    onClickNavigation: (NavigationEvent) -> Unit,
    randomNumbers: Set<Int>,
    editTextStates: List<EditTextState>,
    updateEditTextState: (Int, String) -> Unit,
) {
    Scaffold(
        topBar = {
            TonTopAppBar(
                onClickNavigation = { onClickNavigation(NavigateUp) },
                elevation = 0.dp
            )
        },
        content = {
            TestPhraseScreen(
                onClickNavigation = onClickNavigation,
                modifier = modifier.padding(it),
                randomNumbers = randomNumbers,
                editTextStates = editTextStates,
                updateEditTextState = updateEditTextState
            )
        }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TestPhraseScreen(
    modifier: Modifier,
    onClickNavigation: (NavigationEvent) -> Unit,
    randomNumbers: Set<Int>,
    editTextStates: List<EditTextState>,
    updateEditTextState: (Int, String) -> Unit,
) {


    val (focusNum1, focusNum2, focusNum3) = FocusRequester.createRefs()
    val keyboardController = LocalSoftwareKeyboardController.current

    val num1 = randomNumbers.elementAt(0)
    val num2 = randomNumbers.elementAt(1)
    val num3 = randomNumbers.elementAt(2)

    TonSurface(
        modifier = modifier,
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            item {
                TonLottieAnimation(
                    lottieCompositionSpec = LottieCompositionSpec.Asset("anim/test_time.json"),
                    modifier = Modifier.size(128.dp),
                )
                Spacer(Modifier.height(8.dp))
            }

            item {
                Text(
                    modifier = Modifier
                        .padding(PaddingValues(horizontal = 16.dp)),
                    text = stringResource(id = R.string.title_test_time),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.secondary,
                    style = MaterialTheme.typography.body1.copy(fontSize = 24.sp),
                )
                Spacer(Modifier.height(8.dp))
            }

            item {
                Text(
                    modifier = Modifier
                        .padding(PaddingValues(horizontal = 16.dp)),
                    text = stringResource(
                        id = R.string.format_msg_test_time_to_enter_words,
                        num1,
                        num2,
                        num3
                    ),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.caption,
                )
                Spacer(Modifier.height(16.dp))
            }

            item {
                TonTextField(
                    modifier = Modifier.focusRequester(focusNum1),
                    value = editTextStates[INDEX_ZERO].text,
                    singleLine = true,
                    onValueChange = { newText ->
                        updateEditTextState(INDEX_ZERO, newText)
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = {
                        focusNum2.requestFocus()
                    }),
                    label = { Text(text = "$num1:") },
                )
                Spacer(Modifier.height(8.dp))
            }
            item {
                TonTextField(
                    modifier = Modifier.focusRequester(focusNum2),
                    value = editTextStates[INDEX_ONE].text,
                    singleLine = true,
                    onValueChange = { newText ->
                        updateEditTextState(INDEX_ONE, newText)
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = {
                        focusNum3.requestFocus()
                    }),
                    label = { Text(text = "$num2:") },
                )
                Spacer(Modifier.height(8.dp))
            }
            item {
                TonTextField(
                    modifier = Modifier.focusRequester(focusNum3),
                    value = editTextStates[INDEX_TWO].text,
                    singleLine = true,
                    onValueChange = { newText ->
                        updateEditTextState(INDEX_TWO, newText)
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                    }),
                    label = { Text(text = "$num3:") },
                )
                Spacer(Modifier.height(8.dp))
            }

            item {
                Spacer(Modifier.height(24.dp))
                TonButton(
                    text = stringResource(id = R.string.btn_continue),
                    onClick = { onClickNavigation(RouterCreateWallet.GeneratePhrase) },
                )
            }

        }
    }
}


@Preview
@Composable
private fun TestPhrasePreview() {
    TonWalletTheme {
        TestPhraseScaffoldScreen(
            onClickNavigation = {},
            updateEditTextState = { _, _ -> },
            editTextStates = listOf(
                EditTextState(),
                EditTextState(),
                EditTextState(),
            ),
            randomNumbers = setOf(12, 3, 18)
        )
    }
}