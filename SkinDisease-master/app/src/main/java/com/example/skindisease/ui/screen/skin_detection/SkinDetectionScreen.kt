package com.example.skindisease.ui.screen.skin_detection

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Camera
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.skindisease.R
import com.example.skindisease.ui.component.Loading
import com.example.skindisease.ui.navhost.MainRoutes
import com.example.skindisease.utils.toUri
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkinDetectionScreen(
    modifier: Modifier = Modifier,
    outputDir: File,
    imageUri: Uri?,
    navController: NavController,
    mainNavController: NavController,
    onChangeUri: (Uri?) -> Unit,
    firebaseUser: FirebaseUser?,
    snackBarHostState: SnackbarHostState
) {
    val viewModel = koinViewModel<SkinDetectionViewModel>()
    LaunchedEffect(Unit) {
        viewModel.showDetectButton(imageUri != null)
        if (imageUri != null) {
            viewModel.setFile(File(imageUri.path))
        }
    }

    val state = viewModel.state.value
    val scope = rememberCoroutineScope()
    val bottomSheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = state.skipPartiallyExpanded)
    val labelStyle = MaterialTheme.typography.titleMedium
    val valueStyle = MaterialTheme.typography.bodyMedium
    val context = LocalContext.current
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                val result =
                    saveImageToAppDirectory(context = context, uri = uri, outputDir = outputDir)
                onChangeUri("file://${result}".toUri())
                viewModel.setFile(result)
                viewModel.showDetectButton(true)
            }
        }
    )

    Column(
        modifier = modifier
            .padding(10.dp)
    ) {
        imageUri?.let {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .clip(MaterialTheme.shapes.medium)
                    .clickable {
                        scope.launch {
                            viewModel.toggleBottomSheet()
                        }
                    },
                model = it,
                contentDescription = "Image",
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(10.dp))

            if (state.showDetectButton) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onClick = {
                        viewModel.detect(firebaseUser?.email ?: "")
                    }
                ) {
                    Text(stringResource(R.string.detect))
                }
            }

            if (state.detectionResult != null) {
                Log.d(TAG, "SkinDetectionScreen: Detected")
                LaunchedEffect(Unit) {
                    mainNavController.navigate(
                        MainRoutes.DetectionResultScreen(
                            state.detectionResult.id
                        )
                    )
                    viewModel.setDetectionResult(null)
                    onChangeUri(null)
                }
            }
        } ?: run {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .clip(MaterialTheme.shapes.medium)
                    .clickable {
                        scope.launch {
                            viewModel.toggleBottomSheet()
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(stringResource(R.string.tap_to_detect_image))
            }
        }

        Spacer(Modifier.height(30.dp))

        Loading(
            modifier = Modifier
                .size(70.dp)
                .align(Alignment.CenterHorizontally),
            loading = state.loading
        )
    }
    if (state.showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.toggleBottomSheet() },
            sheetState = bottomSheetState,
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(R.string.select_image_from), style = labelStyle)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column {
                        IconButton(onClick = {
                            scope.launch { bottomSheetState.hide() }
                                .invokeOnCompletion {
                                    if (!bottomSheetState.isVisible) {
                                        viewModel.toggleBottomSheet()
                                    }
                                }
                            singlePhotoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }) {
                            Icon(imageVector = Icons.Outlined.Image, contentDescription = "Gallery")
                        }
                        Text("Gallery", style = valueStyle)
                    }
                    Spacer(Modifier.width(100.dp))
                    Column {
                        IconButton(onClick = {
                            scope.launch { bottomSheetState.hide() }
                                .invokeOnCompletion {
                                    if (!bottomSheetState.isVisible) {
                                        viewModel.toggleBottomSheet()
                                    }
                                }
                            mainNavController.navigate(MainRoutes.CameraScreen)
                        }) {
                            Icon(imageVector = Icons.Outlined.Camera, contentDescription = "Camera")
                        }
                        Text("Camera", style = valueStyle)
                    }
                }
                Spacer(Modifier.height(50.dp))
            }
        }
    }

    if (state.error.isNotBlank())
        LaunchedEffect(Unit) {
            snackBarHostState.showSnackbar(message = state.error, withDismissAction = true)
        }

    if (state.success.isNotBlank())
        LaunchedEffect(Unit) {
            snackBarHostState.showSnackbar(message = state.success, withDismissAction = true)
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
