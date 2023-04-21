package org.ton.wallet.feature.create.ui.phrase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.ton.wallet.feature.create.domain.usecase.GetMnemonicUseCase
import javax.inject.Inject

@HiltViewModel
class PhraseViewModel @Inject constructor(
    private val getMnemonicUseCase: GetMnemonicUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<PhraseUiState>(PhraseUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getMnemonic()
    }

    private fun getMnemonic() {
        viewModelScope.launch {
            getMnemonicUseCase().fold(
                onSuccess = { wordList ->
                    _uiState.update { PhraseUiState.Success(wordList) }
                },
                onFailure = {
                    _uiState.update { PhraseUiState.Error }
                }
            )
        }
    }

}


sealed interface PhraseUiState {
    object Loading : PhraseUiState
    object Error : PhraseUiState
    data class Success(val wordList: List<String>) : PhraseUiState
}