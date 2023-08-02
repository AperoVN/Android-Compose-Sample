package com.apero.sample.ui.screen.selectphoto

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apero.sample.data.model.AlbumMedia
import com.apero.sample.data.model.MediaModel
import com.apero.sample.data.prefs.app.AppPreferences
import com.apero.sample.data.provider.media.MediaProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ImageSelectorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val mediaProvider: MediaProvider,
    private val dataStore: AppPreferences
) : ViewModel() {

    private val _imageSelectorUiState = MutableStateFlow(ImageSelectorUiState())
    val imageSelectorUiState: StateFlow<ImageSelectorUiState> = _imageSelectorUiState.asStateFlow()

    private val _selectMediaState: MutableSharedFlow<MediaModel> = MutableSharedFlow()
    val selectMediaState: SharedFlow<MediaModel> = _selectMediaState.asSharedFlow()

    init {
        viewModelScope.launch {
            dataStore.isFirstTimeAskingPermission.distinctUntilChanged()
                .collectLatest { isFirstRequestPermission ->
                    _imageSelectorUiState.update { it.copy(isFirstTimeAskPermission = isFirstRequestPermission) }
                }
        }
    }

    fun updateAlbumSelected(albumSelected: AlbumMedia) {
        _imageSelectorUiState.update { it.copy(albumSelected = albumSelected) }
    }

    fun updateMediaSelected(mediaSelected: MediaModel?) {
        _imageSelectorUiState.update { it.copy(mediaSelected = mediaSelected) }
    }

    fun selectMedia(mediaSelected: MediaModel?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (mediaSelected != null) {
                _selectMediaState.emit(mediaSelected)
            }
        }
    }

    fun updatePermission(hasPermission: Boolean) {
        viewModelScope.launch {
            val listAlbum = if (hasPermission) {
                mediaProvider.getPictureAlbums()
            } else emptyList()
            _imageSelectorUiState.update {
                it.copy(
                    hasPermission = hasPermission,
                    listAlbum = listAlbum,
                    albumSelected = listAlbum.firstOrNull()
                )
            }
        }
    }

    fun updateFirstTimeRequestPermission() {
        viewModelScope.launch {
            dataStore.hasFirstTimeAskingPermissions()
        }
    }
}

data class ImageSelectorUiState(
    val listAlbum: List<AlbumMedia> = emptyList(),
    val isLoading: Boolean = false,
    val albumSelected: AlbumMedia? = null,
    val mediaSelected: MediaModel? = null,
    val hasPermission: Boolean = false,
    val isFirstTimeAskPermission: Boolean = false
)
