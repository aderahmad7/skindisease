package com.example.skindisease.ui.screen.bottom_nav

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.skindisease.ui.navhost.BottomNavRoute

class BottomNavViewModel : ViewModel() {
    private val _state = mutableStateOf(BottomNavContract.State())
    val state: State<BottomNavContract.State> = _state

    fun updateBottomNavRoute(bottomNavRoute: BottomNavRoute) {
        _state.value = state.value.copy(currentBottomNavRoute = bottomNavRoute)
    }
}