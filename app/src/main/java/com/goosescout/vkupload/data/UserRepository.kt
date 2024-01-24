package com.goosescout.vkupload.data

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract
import com.goosescout.vkupload.model.Album
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope

interface UserRepository {
    fun getLoginActivityResultContract(): ActivityResultContract<Collection<VKScope>, VKAuthenticationResult>
    fun logout()
    fun isLoggedIn(): Boolean
    suspend fun getNames(): List<String?>
    suspend fun getAlbums(): List<Album>
    suspend fun uploadPhotos(albumId: Int, uris: List<Uri>)
}