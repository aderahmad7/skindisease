package com.example.skindisease.ui.screen.edit_profile

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.skindisease.R
import com.example.skindisease.ui.component.Loading
import com.example.skindisease.ui.theme.topBarColor
import com.example.skindisease.utils.toUri
import org.koin.androidx.compose.koinViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavHostController,
    modifier: Modifier,
    outputDir: File,
) {

    val viewModel = koinViewModel<EditProfileViewModel>()
    val state = viewModel.state.value
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                val result =
                    saveImageToAppDirectory(context = context, uri = uri, outputDir = outputDir)
                viewModel.setImageUri("file://${result}")
            }
        }
    )

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIosNew,
                            contentDescription = null
                        )
                    }
                },
                colors = topBarColor,
                title = {
                    Text(stringResource(R.string.edit_profile))
                })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            AsyncImage(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(CircleShape)
                    .clickable {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                contentScale = ContentScale.Crop,
                model = state.imageUrl ?: R.drawable.profile_image,
                contentDescription = null,
            )

            Spacer(Modifier.height(30.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                value = state.fullName,
                onValueChange = { viewModel.changeInput(0, it) },
                label = { Text(stringResource(R.string.full_name)) },
                singleLine = true,
                enabled = !state.loading
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                value = state.email,
                onValueChange = { viewModel.changeInput(1, it) },
                label = { Text(stringResource(R.string.email)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                enabled = false
            )

            Spacer(Modifier.height(30.dp))

            Loading(
                modifier = Modifier
                    .size(70.dp)
                    .align(Alignment.CenterHorizontally),
                loading = state.loading
            )

            Spacer(Modifier.height(30.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.updateUser()
                },
                shape = MaterialTheme.shapes.medium
            ) {
                Text(stringResource(R.string.save))
            }
        }
    }

    if (state.error.isNotBlank())
        LaunchedEffect(Unit) {
            snackbarHostState.showSnackbar(message = state.error, withDismissAction = true)
        }

    if (state.success.isNotBlank())
        LaunchedEffect(Unit) {
            snackbarHostState.showSnackbar(message = state.success, withDismissAction = true)
        }
}

private fun saveImageToAppDirectory(context: Context, uri: Uri, outputDir: File): File {
    val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
    val outputFile = File(outputDir, "selected_image_${System.currentTimeMillis()}.jpg")

    if (inputStream != null) {
        var outputStream: OutputStream? = null
        try {
            outputStream = FileOutputStream(outputFile)
            inputStream.copyTo(outputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inputStream.close()
            outputStream?.close()
        }
    }

    return outputFile
}