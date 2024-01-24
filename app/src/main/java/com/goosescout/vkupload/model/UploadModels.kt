package com.goosescout.vkupload.model

data class VKFileUploadInfo(val server: String, val photosList: String, val hash: String)

data class VKServerUploadInfo(val uploadUrl: String, val albumId: Int, val userId: Int)

