package com.goosescout.vkupload.data.impl

import android.net.Uri
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import com.goosescout.vkupload.data.UserRepository
import com.goosescout.vkupload.model.Album
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope
import com.vk.sdk.api.photos.PhotosService
import com.vk.sdk.api.photos.dto.PhotosGetAlbumsResponseDto
import com.vk.sdk.api.users.UsersService
import com.vk.sdk.api.users.dto.UsersUserFullDto
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class UserRepositoryImpl : UserRepository {
    override fun getLoginActivityResultContract(): ActivityResultContract<Collection<VKScope>, VKAuthenticationResult> {
        return VK.getVKAuthActivityResultContract()
    }

    override fun logout() {
        VK.logout()
    }

    override fun isLoggedIn(): Boolean {
        return VK.isLoggedIn()
    }

    override suspend fun getNames(): List<String?> {
        return suspendCancellableCoroutine { continuation ->
            VK.execute(UsersService().usersGet(), object : VKApiCallback<List<UsersUserFullDto>> {
                override fun success(result: List<UsersUserFullDto>) {
                    Log.d("VK", "Names received successfully")
                    if (result.isNotEmpty()) {
                        val currentUser = result[0]
                        val names = listOf(currentUser.firstName, currentUser.lastName)
                        continuation.resume(names)
                    } else {
                        continuation.resume(emptyList())
                    }
                }

                override fun fail(error: Exception) {
                    continuation.resumeWithException(error)
                }
            })
        }
    }

    override suspend fun getAlbums(): List<Album> {
        return suspendCancellableCoroutine { continuation ->
            VK.execute(PhotosService().photosGetAlbums(needCovers = true), object :  VKApiCallback<PhotosGetAlbumsResponseDto> {
                override fun success(result: PhotosGetAlbumsResponseDto) {
                    Log.d("VK", "Albums received successfully")
                    continuation.resume(result.items.map {
                        Album(it.id, it.title, it.size, it.thumbSrc!!)
                    })
                }

                override fun fail(error: Exception) {
                    continuation.resumeWithException(error)
                }
            })
        }
    }

    override suspend fun uploadPhotos(albumId: Int, uris: List<Uri>) {
        return suspendCancellableCoroutine { continuation ->
            VK.execute(VKUploadToAlbumCommand(albumId, uris), object : VKApiCallback<Unit> {
                override fun success(result: Unit) {
                    Log.d("VK", "Photos uploaded successfully")
                    continuation.resume(Unit)
                }

                override fun fail(error: Exception) {
                    continuation.resumeWithException(error)
                }
            })
        }
    }
}