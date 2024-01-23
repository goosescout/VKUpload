package com.goosescout.vkupload.ui.state

import com.goosescout.vkupload.model.Album

data class UserUiState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val firstName: String = "",
    val lastName: String = "",
    val albums: List<Album> = emptyList()
)