package com.goosescout.vkupload.ui.upload

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.goosescout.vkupload.ui.state.UserViewModel

@Composable
fun LogoutButton(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel,
    onLogout: () -> Unit = {}
) {
    var isDialogShowing by remember { mutableStateOf(false) }

    Button(
        onClick = { isDialogShowing = true },
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.errorContainer),
        modifier = modifier.wrapContentWidth().wrapContentHeight(),
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.ExitToApp,
            contentDescription = "Logout",
            modifier = Modifier.height(28.dp).width(28.dp)
        )
    }

    if (isDialogShowing) {
        AlertDialog(
            onDismissRequest = { isDialogShowing = false },
            title = { Text("Выход") },
            text = { Text("Вы уверены, что хотите выйти?") },
            confirmButton = {
                Button(
                    onClick = {
                        userViewModel.logout()
                        onLogout()
                        isDialogShowing = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.wrapContentWidth().wrapContentHeight(),
                    contentPadding = PaddingValues(2.dp)
                ) {
                    Text(
                        text = "Подтвердить",
                        fontSize = 18.sp
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = { isDialogShowing = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.wrapContentWidth().wrapContentHeight(),
                    contentPadding = PaddingValues(2.dp)
                ) {
                    Text(
                        text = "Отмена",
                        fontSize = 18.sp
                    )
                }
            }
        )
    }
}