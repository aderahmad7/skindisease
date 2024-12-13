package com.example.skindisease.ui.screen.profile

import com.google.firebase.auth.FirebaseUser

class ProfileContract {
    data class State(
        val loading: Boolean = false,
        val showConfirmDialog: Boolean = false,
        val user: FirebaseUser? = null,
        val error: String = ""
    )
}