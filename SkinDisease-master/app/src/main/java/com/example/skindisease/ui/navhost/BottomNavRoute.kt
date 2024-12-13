package com.example.skindisease.ui.navhost

import kotlinx.serialization.Serializable

sealed interface BottomNavRoute {
    @Serializable
    data object HomeScreen : BottomNavRoute

    @Serializable
    data object SkinDetectionScreen : BottomNavRoute

    @Serializable
    data object ProfileScreen : BottomNavRoute
}