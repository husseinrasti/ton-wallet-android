package org.ton.wallet.feature.create.ui.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.ton.wallet.feature.create.domain.usecase.GeneratePhrasesUseCase
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val generatePhrasesUseCase: GeneratePhrasesUseCase,
) : ViewModel() {

    private val _uiState = MutableSharedFlow<StartUiState>()
    val uiState = _uiState.asSharedFlow()

    fun generatePhrases() {
        viewModelScope.launch {
            _uiState.emit(StartUiState.Loading)
            generatePhrasesUseCase().fold(
                onSuccess = { isSuccess ->
                    _uiState.emit(
                        if (isSuccess) StartUiState.Success
                        else StartUiState.Error
                    )
                },
                onFailure = {
                    it.printStackTrace()
                    _uiState.emit(StartUiState.Error)
                }
            )
        }
    }

}


sealed interface StartUiState {
    object Idle : StartUiState
    object Loading : StartUiState
    object Error : StartUiState
    object Success : StartUiState
}