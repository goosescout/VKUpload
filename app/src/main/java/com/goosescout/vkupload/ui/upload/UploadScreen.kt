package com.goosescout.vkupload.ui.upload

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.goosescout.vkupload.ui.state.UserViewModel

@Composable
fun UploadScreen(
    userViewModel: UserViewModel,
    navController: NavController
) {
    val uiState by userViewModel.uiState.collectAsStateWithLifecycle()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Text(
            text = "Приветствуем, ${uiState.firstName}",
            style = MaterialTheme.typography.titleMedium
        )
        LogoutButton(
            onLogout = {
                navController.navigate("welcome")
            }
        )
    }
}