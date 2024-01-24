package com.goosescout.vkupload.ui.upload

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goosescout.vkupload.ui.state.UserViewModel

@Composable
fun AlbumsList(
    userViewModel: UserViewModel
) {
    val uiState by userViewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 150.dp)
        ) {
            items(uiState.albums.size) { index ->
                AlbumItem(
                    album = uiState.albums[index],
                    modifier = Modifier.padding(8.dp).height(240.dp),
                    uploadPhotos = { albumId, uris ->
                        userViewModel.uploadPhotos(albumId, uris)
                    }
                )
            }
        }
    }
}