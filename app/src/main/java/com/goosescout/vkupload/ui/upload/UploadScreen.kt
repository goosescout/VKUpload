package com.goosescout.vkupload.ui.upload

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.goosescout.vkupload.ui.state.UserViewModel

@Composable
fun UploadScreen(
    userViewModel: UserViewModel,
    navController: NavController
) {
    val uiState by userViewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(12.dp, 16.dp)) {
        if (uiState.isLoading) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        } else {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                ) {
                    Text(
                        text = "Привет, ${uiState.firstName}!",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Divider(modifier = Modifier.padding(top = 12.dp, bottom = 12.dp))
                    Text(
                        text = "Кликните по альбому, чтобы начать загружать в него фотографии:",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    AlbumsList(userViewModel = userViewModel)

                    Spacer(Modifier.weight(1f))

                    Divider(modifier = Modifier.padding(top = 12.dp, bottom = 12.dp))
                    LogoutButton(
                        userViewModel,
                        onLogout = {
                            navController.navigate("welcome")
                        }
                    )
                }
            }
        }
    }
}