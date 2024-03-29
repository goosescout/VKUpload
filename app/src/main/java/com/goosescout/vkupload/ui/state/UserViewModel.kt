package com.goosescout.vkupload.ui.state

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.goosescout.vkupload.data.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel (
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UserUiState(
        isLoading = true,
        isLoggedIn = userRepository.isLoggedIn()
    ))
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    init {
        refreshAll()
    }

    fun getLoginActivityResultContract() = userRepository.getLoginActivityResultContract()

    fun login() {
        refreshAll()
    }

    fun logout() {
        userRepository.logout()
        _uiState.update { it.copy(isLoggedIn = false) }
    }

    fun uploadPhotos(albumId: Int, uris: List<Uri>) {
        _uiState.update {
            it.copy(albums = it.albums.map { album -> if (album.id == albumId) album.copy(isLoading = true) else album })
        }

        viewModelScope.launch {
            val uploadResultDeferred = async { userRepository.uploadPhotos(albumId, uris) }
            uploadResultDeferred.await()

            _uiState.update {
                it.copy(albums = it.albums.map { album -> if (album.id == albumId) album.copy(size = album.size + uris.size, isLoading = false) else album })
            }
        }
    }

    fun refreshAll() {
        if (!userRepository.isLoggedIn()) return

        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val namesDeferred = async { userRepository.getNames() }
            val albumsDeferred = async { userRepository.getAlbums() }

            val names = namesDeferred.await()
            val albums = albumsDeferred.await()

            _uiState.update {
                it.copy(
                    isLoading = false,
                    isLoggedIn = true,
                    firstName = names[0] ?: "",
                    lastName = names[1] ?: "",
                    albums = albums
                )

            }
        }
    }

    companion object {
        fun provideFactory(
            userRepository: UserRepository
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return UserViewModel(userRepository) as T
            }
        }

    }
}