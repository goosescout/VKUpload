package com.goosescout.vkupload.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.goosescout.vkupload.data.AppContainer
import com.goosescout.vkupload.ui.state.UserViewModel
import com.goosescout.vkupload.ui.upload.UploadScreen
import com.goosescout.vkupload.ui.welcome.WelcomeScreen

@Composable
fun VKUploadApp(
    appContainer: AppContainer
) {
    val navController = rememberNavController()

    val userViewModel: UserViewModel = viewModel(
        factory = UserViewModel.provideFactory(appContainer.userRepository)
    )
    val uiState by userViewModel.uiState.collectAsStateWithLifecycle()
    NavHost(
        navController = navController,
        startDestination = if (uiState.isLoggedIn) "upload" else "welcome"
    ) {
        composable("welcome") {
            WelcomeScreen(
                userViewModel = userViewModel,
                navController = navController
            )
        }
        composable("upload") {
            UploadScreen(
                userViewModel = userViewModel,
                navController = navController
            )
        }
    }
}