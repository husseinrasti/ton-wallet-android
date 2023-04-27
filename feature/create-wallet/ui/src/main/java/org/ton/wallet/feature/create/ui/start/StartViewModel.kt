package org.ton.wallet.feature.create.ui.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.ton.wallet.feature.create.domain.usecase.GetMnemonicUseCase
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val getMnemonicUseCase: GetMnemonicUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<StartUiState>(StartUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getMnemonic()
    }

    private fun getMnemonic() {
        viewModelScope.launch {
            getMnemonicUseCase().fold(
                onSuccess = { wordList ->
                    _uiState.update { StartUiState.Success(wordList) }
                },
                onFailure = {
                    _uiState.update { StartUiState.Error }
                }
            )
        }
    }

}


sealed interface StartUiState {
    object Loading : StartUiState
    object Error : StartUiState
    data class Success(val wordList: List<String>) : StartUiState
}