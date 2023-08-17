package com.apero.sample.ui.screen.crop

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PsCropViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _cropUiState = MutableStateFlow(CropUIState())
    val cropUiState: StateFlow<CropUIState> = _cropUiState.asStateFlow()

    private val args = savedStateHandle.get<CropScreenArgs>("args")!!

    init {
        updateBitmapInput(Uri.parse(args.imageUri))
    }

    private fun updateBitmapInput(dataInput: Uri?) {
        _cropUiState.update { it.copy(dataInput = dataInput) }
    }
}

data class CropUIState(
    val dataInput: Uri? = null,
)
