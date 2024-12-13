package com.example.skindisease.ui.screen.splash

import com.google.firebase.auth.FirebaseUser

class SplashContract {
    data class State(
        val user: FirebaseUser? = null,
        val loading: Boolean = false,
        val screenReady: Boolean = false,
        val error: String = ""
    )
}