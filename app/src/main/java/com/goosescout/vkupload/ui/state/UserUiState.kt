package com.goosescout.vkupload.ui.state

data class UserUiState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val firstName: String = "",
    val lastName: String = ""
)