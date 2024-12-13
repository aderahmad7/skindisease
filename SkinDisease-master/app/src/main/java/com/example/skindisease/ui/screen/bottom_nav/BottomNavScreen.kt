package com.example.skindisease.ui.screen.bottom_nav

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Camera
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.skindisease.ui.navhost.BottomNavRoute
import com.example.skindisease.ui.navhost.BottomNavbarNavHost
import com.example.skindisease.ui.navhost.MainRoutes
import com.example.skindisease.ui.theme.topBarColor
import com.google.firebase.auth.FirebaseUser
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    mainNavHostController: NavHostController,
    imageUri: Uri?,
    onNavigate: (MainRoutes) -> Unit,
    onChangeUriRequest: (Uri?) -> Unit,
    setUser: (FirebaseUser?) -> Unit,
    firebaseUser: FirebaseUser?,
    outputDirectory: java.io.File
) {
    val viewModel = koinViewModel<BottomNavViewModel>()
    val state = viewModel.state.value
    val topBar = @Composable {
        TopAppBar(
            colors = topBarColor,
            title = {
                when (state.currentBottomNavRoute) {
                    BottomNavRoute.HomeScreen -> {
                        Text("Home")
                    }

                    BottomNavRoute.ProfileScreen -> {
                        Text("Profile")
                    }

                    BottomNavRoute.SkinDetectionScreen -> {
                        Text("Detect your skin")
                    }
                }
            }
        )
    }
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = modifier.navigationBarsPadding(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = topBar,
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .padding(horizontal = 32.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = {
                        viewModel.updateBottomNavRoute(BottomNavRoute.HomeScreen)
                        navHostController.navigate(BottomNavRoute.HomeScreen) {
                            popUpTo(BottomNavRoute.HomeScreen) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }) {
                        Icon(imageVector = Icons.Outlined.Home, contentDescription = "Home")
                    }

                    IconButton(onClick = {
                        onChangeUriRequest(null)
                        viewModel.updateBottomNavRoute(BottomNavRoute.SkinDetectionScreen)
                        navHostController.navigate(BottomNavRoute.SkinDetectionScreen) {
                            popUpTo(BottomNavRoute.SkinDetectionScreen) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }) {
                        Icon(imageVector = Icons.Outlined.Camera, contentDescription = "Scan")
                    }

                    IconButton(onClick = {
                        viewModel.updateBottomNavRoute(BottomNavRoute.ProfileScreen)
                        navHostController.navigate(BottomNavRoute.ProfileScreen) {
                            popUpTo(BottomNavRoute.ProfileScreen) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }) {
                        Icon(imageVector = Icons.Outlined.Person, contentDescription = "Profile")
                    }
                }
            }
        }
    ) { padding ->
        BottomNavbarNavHost(
            modifier = Modifier.padding(padding),
            navController = navHostController,
            imageUri = imageUri,
            onChangeUri = onChangeUriRequest,
            mainNavController = mainNavHostController,
            onBottomNavNavigate = viewModel::updateBottomNavRoute,
            firebaseUser = firebaseUser,
            setUser = setUser,
            updateScaffoldTitle = viewModel::updateBottomNavRoute,
            outputDirectory = outputDirectory,
            snackBarHostState = snackbarHostState
        )
    }
}