package com.example.skindisease.ui.navhost

import android.net.Uri
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.skindisease.ui.screen.home.HomeScreen
import com.example.skindisease.ui.screen.profile.ProfileScreen
import com.example.skindisease.ui.screen.skin_detection.SkinDetectionScreen
import com.google.firebase.auth.FirebaseUser
import java.io.File

@Composable
fun BottomNavbarNavHost(
    modifier: Modifier,
    navController: NavHostController,
    mainNavController: NavHostController,
    onBottomNavNavigate: (BottomNavRoute) -> Unit,
    imageUri: Uri?,
    onChangeUri: (Uri?) -> Unit,
    setUser: (FirebaseUser?) -> Unit,
    firebaseUser: FirebaseUser?,
    updateScaffoldTitle: (BottomNavRoute) -> Unit,
    outputDirectory: File,
    snackBarHostState: SnackbarHostState
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavRoute.HomeScreen,
    ) {
        composable<BottomNavRoute.HomeScreen> {
            HomeScreen(
                modifier = modifier,
                navController = navController,
                mainNavController = mainNavController,
                updateScaffoldTitle = updateScaffoldTitle,
                firebaseUser = firebaseUser,
                onBottomNavNavigate = onBottomNavNavigate
            )
        }

        composable<BottomNavRoute.SkinDetectionScreen> {
            SkinDetectionScreen(
                modifier = modifier,
                navController = navController,
                imageUri = imageUri,
                onChangeUri = onChangeUri,
                mainNavController = mainNavController,
                outputDir = outputDirectory,
                firebaseUser = firebaseUser,
                snackBarHostState = snackBarHostState
            )
        }

        composable<BottomNavRoute.ProfileScreen> {
            ProfileScreen(
                modifier = modifier,
                navController = navController,
                firebaseUser = firebaseUser,
                setUser = setUser,
                mainNavController = mainNavController
            )
        }
    }
}