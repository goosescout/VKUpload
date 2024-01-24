package com.goosescout.vkupload.model

data class Album(
    val id: Int,
    val title: String,
    val size: Int,
    val thumbSrc: String,
    val isLoading: Boolean = false
)
