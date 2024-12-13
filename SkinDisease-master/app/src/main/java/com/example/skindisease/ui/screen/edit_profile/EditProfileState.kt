package com.example.skindisease.ui.screen.edit_profile

import android.net.Uri
import androidx.compose.ui.text.input.TextFieldValue

data class EditProfileState(
    val loading: Boolean = false,
    val error: String = "",
    val success: String = "",
    val fullName: TextFieldValue = TextFieldValue(""),
    val email: TextFieldValue = TextFieldValue(""),
    val imageUrl: Uri? = null
)
