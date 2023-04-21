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
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = _uiState.value
    )

    fun getMnemonic() {
        viewModelScope.launch {
            getMnemonicUseCase.invoke().fold(
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