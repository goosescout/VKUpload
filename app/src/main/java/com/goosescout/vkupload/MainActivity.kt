package com.goosescout.vkupload

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goosescout.vkupload.ui.theme.VKUploadTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope
import com.vk.sdk.api.photos.PhotosService
import com.vk.sdk.api.photos.dto.PhotosGetAlbumsResponseDto
import com.vk.sdk.api.users.UsersService
import com.vk.sdk.api.users.dto.UsersUserFullDto

class MainActivity : ComponentActivity() {
    private lateinit var authLauncher: ActivityResultLauncher<Collection<VKScope>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authLauncher = VK.login(this) { result : VKAuthenticationResult ->
            when (result) {
                is VKAuthenticationResult.Success -> {
                    // User passed authorization
                    getUserAlbums()
                    getUserName()
                }
                is VKAuthenticationResult.Failed -> {
                    // User didn't pass authorization
                }
            }
        }
        setContent {
            VKUploadTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Greeting("Android")
                        Spacer(modifier = Modifier.height(32.dp))
                        VKLoginButton(onClick = { authLauncher.launch(listOf(VKScope.PHOTOS)) })
                    }
                }
            }
        }
    }

    private fun getUserAlbums() {
        VK.execute(PhotosService().photosGetAlbums(), object : VKApiCallback<PhotosGetAlbumsResponseDto> {
            override fun success(result: PhotosGetAlbumsResponseDto) {
                // print all albums
                result.items.forEach { album ->
                    Log.d("VK", album.title)
                }
            }

            override fun fail(error: Exception) {
                Log.d("VK", "fail")
                error.message?.let { Log.d("VK", it) }
            }
        })
    }

    private fun getUserName() {
        VK.execute(UsersService().usersGet(), object : VKApiCallback<List<UsersUserFullDto>> {
            override fun success(result: List<UsersUserFullDto>) {
                // Here you get the user's information
                if (result.isNotEmpty()) {
                    val user = result[0]
                    Log.d("VK", "User name: ${user.firstName} ${user.lastName}")
                }
            }

            override fun fail(error: Exception) {
                // Handle error
                Log.e("Error", error.message ?: "Unknown error")
            }
        })
    }

    private fun uploadPhoto() {
        PhotosService().photosGetUploadServer()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun VKLoginButton(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("Login with VK")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VKUploadTheme {
        Greeting("Android")
    }
}