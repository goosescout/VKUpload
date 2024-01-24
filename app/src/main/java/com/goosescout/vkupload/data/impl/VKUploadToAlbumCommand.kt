package com.goosescout.vkupload.data.impl

import android.net.Uri
import android.util.Log
import com.goosescout.vkupload.model.VKFileUploadInfo
import com.goosescout.vkupload.model.VKServerUploadInfo
import com.vk.api.sdk.VKApiJSONResponseParser
import com.vk.api.sdk.VKApiManager
import com.vk.api.sdk.VKApiProgressListener
import com.vk.api.sdk.VKHttpPostCall
import com.vk.api.sdk.VKMethodCall
import com.vk.api.sdk.exceptions.VKApiIllegalResponseException
import com.vk.api.sdk.internal.ApiCommand
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class VKUploadToAlbumCommand(private val albumId: Int,
                             private val photos: List<Uri> = listOf(),
                             private val progressListener: VKApiProgressListener? = null): ApiCommand<Unit>() {
    override fun onExecute(manager: VKApiManager) {
        if (photos.isNotEmpty()) {
            val chunks = photos.chunked(5)

            chunks.forEach { chunk ->
                val uploadInfo = getServerUploadInfo(manager)
                Log.d("VK", "Server upload info: ${uploadInfo.uploadUrl}")

                val fileUploadCall = VKHttpPostCall.Builder()
                    .url(uploadInfo.uploadUrl)
                    .timeout(TimeUnit.MINUTES.toMillis(5))
                    .retryCount(1)
                for ((index, photo) in chunk.withIndex()) {
                    fileUploadCall.args("file${index + 1}", photo, "image.jpg")
                }

                val fileUploadInfo = manager.execute(fileUploadCall.build(), progressListener, FileUploadInfoParser())
                getSaveInfo(manager, fileUploadInfo)
            }
        }
    }

    private fun getServerUploadInfo(manager: VKApiManager): VKServerUploadInfo {
        val uploadInfoCall = VKMethodCall.Builder()
            .method("photos.getUploadServer")
            .args("album_id", albumId)
            .version(manager.config.version)
            .build()
        return manager.execute(uploadInfoCall, ServerUploadInfoParser())
    }

    private fun getSaveInfo(manager: VKApiManager, fileUploadInfo: VKFileUploadInfo) {
        val saveCall = VKMethodCall.Builder()
            .method("photos.save")
            .args("server", fileUploadInfo.server)
            .args("album_id", albumId)
            .args("photos_list", fileUploadInfo.photosList)
            .args("hash", fileUploadInfo.hash)
            .version(manager.config.version)
            .build()

        return manager.execute(saveCall, SaveInfoParser())
    }

    private class ServerUploadInfoParser : VKApiJSONResponseParser<VKServerUploadInfo> {
        override fun parse(responseJson: JSONObject): VKServerUploadInfo {
            try {
                val innerResponseJSON = responseJson.getJSONObject("response")
                return VKServerUploadInfo(
                    uploadUrl = innerResponseJSON.getString("upload_url"),
                    albumId = innerResponseJSON.getInt("album_id"),
                    userId = innerResponseJSON.getInt("user_id"))
            } catch (ex: JSONException) {
                throw VKApiIllegalResponseException(ex)
            }
        }
    }

    private class FileUploadInfoParser: VKApiJSONResponseParser<VKFileUploadInfo> {
        override fun parse(responseJson: JSONObject): VKFileUploadInfo {
            try {
                val rootResponse = responseJson.getString("root_response")
                val rootResponseJson = JSONObject(rootResponse)
                return VKFileUploadInfo(
                    server = rootResponseJson.getString("server"),
                    photosList = rootResponseJson.getString("photos_list"),
                    hash = rootResponseJson.getString("hash")
                )
            } catch (ex: JSONException) {
                throw VKApiIllegalResponseException(ex)
            }
        }
    }

    private class SaveInfoParser: VKApiJSONResponseParser<Unit> {
        override fun parse(responseJson: JSONObject) {
            try {
                val innerResponseJson = responseJson.getJSONArray("response").getJSONObject(0)
                // do something if needed
            } catch (ex: JSONException) {
                throw VKApiIllegalResponseException(ex)
            }
        }
    }
}