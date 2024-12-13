package com.example.skindisease.ui.navhost

import kotlinx.serialization.Serializable

sealed interface MainRoutes {

    @Serializable
    data object BottomNavScreen : MainRoutes

    @Serializable
    data object CameraScreen : MainRoutes

    @Serializable
    data object GetStartedScreen : MainRoutes

    @Serializable
    data object LoginScreen : MainRoutes

    @Serializable
    data object ForgotPasswordScreen : MainRoutes

    @Serializable
    data object RegisterScreen : MainRoutes

    @Serializable
    data object HistoryScreen : MainRoutes

    @Serializable
    data class DetectionResultScreen(
        val id: Int
    ) : MainRoutes

    @Serializable
    data object SplashScreen : MainRoutes

    @Serializable
    data object PrivacyPolicyScreen : MainRoutes

    @Serializable
    data object EditProfileScreen : MainRoutes
}