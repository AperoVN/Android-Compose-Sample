package com.apero.sample.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.apero.sample.data.db.model.MovieEntity
import com.apero.sample.data.state.UiText

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HistoryViewModel : ViewModel() {
    private val _historyState: MutableStateFlow<HistoryUIState> =
        MutableStateFlow(HistoryUIState().copy(listHistory = List(10) { MovieEntity.mock() }))
    val historyState = _historyState.asStateFlow()
}

data class HistoryUIState(
    val listHistory: List<MovieEntity> = emptyList(),
    val message: UiText? = null,
    val isLoading: Boolean = false,
)