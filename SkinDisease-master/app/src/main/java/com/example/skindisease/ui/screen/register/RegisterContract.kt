package com.example.skindisease.ui.screen.register

import androidx.compose.ui.text.input.TextFieldValue
import com.google.firebase.auth.FirebaseUser

class RegisterContract {
    data class State(
        val email: TextFieldValue = TextFieldValue(""),
        val password: TextFieldValue = TextFieldValue(""),
        val fullName: TextFieldValue = TextFieldValue(""),
        val error: String = "",
        val isAgree: Boolean = false,
        val showPassword: Boolean = false,
        val loading: Boolean = false,
        val user: FirebaseUser? = null,
    )
}