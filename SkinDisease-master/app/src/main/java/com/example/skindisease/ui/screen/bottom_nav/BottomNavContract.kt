package com.example.skindisease.ui.screen.bottom_nav

import com.example.skindisease.ui.navhost.BottomNavRoute

class BottomNavContract {
    data class State(
        val currentBottomNavRoute: BottomNavRoute = BottomNavRoute.HomeScreen
    )
}