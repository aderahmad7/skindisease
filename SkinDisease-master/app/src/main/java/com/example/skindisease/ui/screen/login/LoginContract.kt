package com.example.skindisease.ui.screen.login

import androidx.compose.ui.text.input.TextFieldValue
import com.google.firebase.auth.FirebaseUser

class LoginContract {
    data class State(
        val email: TextFieldValue = TextFieldValue(""),
        val password: TextFieldValue = TextFieldValue(""),
        val loading: Boolean = false,
        val showPassword: Boolean = false,
        val user: FirebaseUser? = null,
        val error: String = ""
    )
}