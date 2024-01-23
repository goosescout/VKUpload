package com.goosescout.vkupload.ui.state

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

    private fun refreshAll() {
        if (!userRepository.isLoggedIn()) return

        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val namesDeferred = async { userRepository.getNames() }
            val names = namesDeferred.await()

            _uiState.update {
                it.copy(
                    isLoading = false,
                    isLoggedIn = true,
                    firstName = names[0] ?: "",
                    lastName = names[1] ?: ""
                )

            }
        }
    }

    companion object {
        fun provideFactory(
            userRepository: UserRepository
        ): ViewModelProvider.Factory =object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return UserViewModel(userRepository) as T
            }
        }

    }
}