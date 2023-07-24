package com.apero.sample.ui.screen.history

import androidx.lifecycle.ViewModel
import com.apero.sample.data.db.model.MovieEntity
import com.apero.sample.data.state.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor() :
    ViewModel() {
    private val _historyState: MutableStateFlow<HistoryUIState> =
        MutableStateFlow(HistoryUIState().copy(listHistory = List(10) { MovieEntity.mock() }))
    val historyState = _historyState.asStateFlow()
}

data class HistoryUIState(
    val listHistory: List<MovieEntity> = emptyList(),
    val message: UiText? = null,
    val isLoading: Boolean = false,
)