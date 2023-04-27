package org.ton.wallet.feature.create.ui.phrase.recovery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.ton.wallet.feature.create.domain.usecase.GetMnemonicUseCase
import javax.inject.Inject

@HiltViewModel
class RecoveryPhraseViewModel @Inject constructor(
    private val getMnemonicUseCase: GetMnemonicUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<RecoveryPhraseUiState>(RecoveryPhraseUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getMnemonic()
    }

    private fun getMnemonic() {
        viewModelScope.launch {
            getMnemonicUseCase().fold(
                onSuccess = { wordList ->
                    _uiState.update { RecoveryPhraseUiState.Success(wordList) }
                },
                onFailure = {
                    _uiState.update { RecoveryPhraseUiState.Error }
                }
            )
        }
    }

}


sealed interface RecoveryPhraseUiState {
    object Loading : RecoveryPhraseUiState
    object Error : RecoveryPhraseUiState
    data class Success(val wordList: List<String>) : RecoveryPhraseUiState
}