package com.example.skindisease.ui.screen.detection_result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.skindisease.R
import com.example.skindisease.ui.navhost.MainRoutes
import com.example.skindisease.ui.theme.topBarColor
import com.example.skindisease.utils.toUri
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetectionResultScreen(
    modifier: Modifier = Modifier,
    detectionResult: MainRoutes.DetectionResultScreen,
    navController: NavHostController
) {
    val viewModel = koinViewModel<DetectionResultViewModel>()
    val state = viewModel.state.value

    LaunchedEffect(Unit) {
        viewModel.getDetectionResult(detectionResult.id)
    }

    val labelStyle = MaterialTheme.typography.titleMedium
    val valueStyle = MaterialTheme.typography.bodyMedium
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                colors = topBarColor,
                title = { Text(stringResource(R.string.detection_result)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(10.dp)
        ) {
            state.result?.let { result ->
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = MaterialTheme.shapes.medium
                        )
                        .clip(MaterialTheme.shapes.medium),
                    model = result.uri.toUri(),
                    contentDescription = "Image",
                    contentScale = ContentScale.Crop
                )

                Spacer(Modifier.height(10.dp))
                Spacer(Modifier.height(30.dp))

                Text(
                    text = stringResource(R.string.name),
                    style = labelStyle
                )
                Text(
                    text = result.name,
                    style = valueStyle
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    text = stringResource(R.string.description),
                    style = labelStyle
                )
                Text(
                    text = result.description,
                    style = valueStyle
                )
            }
        }
    }
}