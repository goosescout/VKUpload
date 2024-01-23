package com.goosescout.vkupload.ui.upload

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goosescout.vkupload.ui.state.UserViewModel

@Composable
fun AlbumsList(
    userViewModel: UserViewModel
) {
    val uiState by userViewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier.width(200.dp).height(200.dp).background(Color.Red)
    )
}