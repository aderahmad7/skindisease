package com.example.skindisease.ui.screen.forgot_password

import androidx.compose.ui.text.input.TextFieldValue

class ForgotPasswordContract {
    data class State(
        val email: TextFieldValue = TextFieldValue(),
        val loading: Boolean = false,
        val success: Boolean = false,
        val error: String = ""
    )
}