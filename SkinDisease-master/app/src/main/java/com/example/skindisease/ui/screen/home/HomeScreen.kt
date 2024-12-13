package com.example.skindisease.ui.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.skindisease.R
import com.example.skindisease.ui.component.Animation
import com.example.skindisease.ui.navhost.BottomNavRoute
import com.example.skindisease.ui.navhost.MainRoutes
import com.example.skindisease.ui.screen.component.NothingToShow
import com.example.skindisease.ui.theme.yellow
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    mainNavController: NavHostController,
    updateScaffoldTitle: (BottomNavRoute) -> Unit,
    firebaseUser: FirebaseUser?,
    onBottomNavNavigate: (BottomNavRoute) -> Unit
) {
    val viewModel = koinViewModel<HomeViewModel>()
    val state = viewModel.state.value
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
    ) {
        AnimatedVisibility(visible = scrollState.value < 2) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = yellow)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .clickable {
                                    updateScaffoldTitle(BottomNavRoute.ProfileScreen)
                                    navController.navigate(BottomNavRoute.ProfileScreen) {
                                        popUpTo(BottomNavRoute.ProfileScreen) {
                                            inclusive = true
                                        }
                                    }
                                },
                            model = firebaseUser?.photoUrl ?: R.drawable.profile_image,
                            contentDescription = "Profile Image",
                            contentScale = ContentScale.Crop
                        )
                        Spacer(Modifier.width(10.dp))
                        Text(
                            "Hi, ${firebaseUser?.displayName}",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.width(100.dp),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }

                    IconButton(onClick = {
                        scope.launch {
                            mainNavController.navigate(MainRoutes.HistoryScreen)
                        }
                    }) {
                        Icon(Icons.Outlined.History, contentDescription = "History")
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState)
                .wrapContentHeight()
        ) {
            Spacer(Modifier.height(30.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.medium
                    )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation_1732192241388))
                    Animation(
                        modifier = Modifier.size(300.dp),
                        composition = composition,
                        loop = false
                    )
                    Text(
                        "Welcome to Skin Disease",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Button(
                        shape = MaterialTheme.shapes.medium,
                        onClick = {
                            onBottomNavNavigate(BottomNavRoute.SkinDetectionScreen)
                            navController.navigate(BottomNavRoute.SkinDetectionScreen) {
                                popUpTo(BottomNavRoute.SkinDetectionScreen) {
                                    inclusive = true
                                }
                            }
                        },
                        modifier = Modifier
                    ) {
                        Text(stringResource(R.string.start_detect_your_skin))
                    }

                }
            }
            Spacer(Modifier.height(30.dp))
            Text(
                text = "History",
                style = MaterialTheme.typography.titleLarge
            )

            NothingToShow(show = state.history.isEmpty())
            LazyColumn(
                modifier = Modifier.height((state.history.size * 140).dp)
            ) {
                items(state.history, key = { it.id }) {
                    HistoryItem(
                        modifier = Modifier,
                        data = it,
                        onClick = { result ->
                            scope.launch {
                                mainNavController.navigate(MainRoutes.DetectionResultScreen(result.id))
                            }
                        }
                    )
                }
            }
        }
    }
}