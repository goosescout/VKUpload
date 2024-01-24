package com.goosescout.vkupload.ui.upload

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.goosescout.vkupload.model.Album

@Composable
fun AlbumItem(
    modifier: Modifier = Modifier,
    album: Album,
    onClick: (Album) -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.clickable { onClick(album) },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Column(
            modifier = Modifier.padding(4.dp, 0.dp).fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = album.thumbSrc,
                contentDescription = null,
                modifier = Modifier
                    .width(142.dp)
                    .height(142.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = album.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSecondary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Фотографий: ${album.size}",
                color = MaterialTheme.colorScheme.onSecondary,
            )
        }
    }
}