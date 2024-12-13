package com.example.skindisease.ui.navhost

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.skindisease.MainViewModel
import com.example.skindisease.ui.screen.bottom_nav.BottomNavScreen
import com.example.skindisease.ui.screen.detection_result.DetectionResultScreen
import com.example.skindisease.ui.screen.edit_profile.EditProfileScreen
import com.example.skindisease.ui.screen.forgot_password.ForgotPasswordScreen
import com.example.skindisease.ui.screen.get_started.GetStartedScreen
import com.example.skindisease.ui.screen.history.HistoryScreen
import com.example.skindisease.ui.screen.login.LoginScreen
import com.example.skindisease.ui.screen.privacy_policy.PrivacyPolicyScreen
import com.example.skindisease.ui.screen.register.RegisterScreen
import com.example.skindisease.ui.screen.skin_detection.CameraView
import com.example.skindisease.ui.screen.splash.SplashScreen
import java.io.File
import java.util.concurrent.Executor

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    outputDirectory: File,
    executor: Executor,
    mainViewModel: MainViewModel
) {
    val mainState = mainViewModel.state.value
    NavHost(
        navController = navHostController,
        startDestination = MainRoutes.SplashScreen
    ) {
        composable<MainRoutes.BottomNavScreen> {
            if (mainState.user == null) {
                LaunchedEffect(Unit) {
                    navHostController.popBackStack()
                    navHostController.navigate(MainRoutes.GetStartedScreen)
                }
            }
            val bottomNavHostController = rememberNavController()
            BottomNavScreen(
                navHostController = bottomNavHostController,
                modifier = modifier,
                imageUri = mainState.imageUri,
                onNavigate = {
                    navHostController.navigate(it)
                },
                onChangeUriRequest = mainViewModel::setImageUri,
                mainNavHostController = navHostController,
                setUser = mainViewModel::setUser,
                firebaseUser = mainState.user,
                outputDirectory = outputDirectory
            )
        }

        composable<MainRoutes.CameraScreen> {
            CameraView(
                modifier = modifier,
                outputDirectory = outputDirectory,
                executor = executor,
                navController = navHostController,
                changeUri = mainViewModel::setImageUri
            )
        }

        composable<MainRoutes.GetStartedScreen> {
            GetStartedScreen(
                navController = navHostController,
                modifier = modifier
            )
        }

        composable<MainRoutes.LoginScreen> {
            LoginScreen(
                navController = navHostController,
                modifier = modifier,
                setUser = mainViewModel::setUser
            )
        }

        composable<MainRoutes.ForgotPasswordScreen> {
            ForgotPasswordScreen(
                navController = navHostController,
                modifier = modifier
            )
        }

        composable<MainRoutes.RegisterScreen> {
            RegisterScreen(
                navController = navHostController,
                modifier = modifier,
                setUser = mainViewModel::setUser
            )
        }

        composable<MainRoutes.HistoryScreen> {
            HistoryScreen(modifier = modifier, navController = navHostController)
        }

        composable<MainRoutes.DetectionResultScreen> { navBackStackEntry ->
            val args = navBackStackEntry.toRoute<MainRoutes.DetectionResultScreen>()

            DetectionResultScreen(
                detectionResult = args,
                navController = navHostController,
            )
        }

        composable<MainRoutes.SplashScreen> {
            SplashScreen(
                modifier = modifier,
                navController = navHostController,
                setUser = mainViewModel::setUser
            )
        }

        composable<MainRoutes.PrivacyPolicyScreen> {
            PrivacyPolicyScreen(
                modifier = modifier,
                navController = navHostController
            )
        }

        composable<MainRoutes.EditProfileScreen> {
            EditProfileScreen(
                navController = navHostController,
                modifier = modifier,
                outputDir = outputDirectory
            )
        }
    }
}