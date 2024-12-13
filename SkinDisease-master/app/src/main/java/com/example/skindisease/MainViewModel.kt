package com.example.skindisease

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import androidx.compose.runtime.State as StateCompose

class MainViewModel : ViewModel() {
    private val _state = mutableStateOf(State())
    val state: StateCompose<State> = _state

    fun setImageUri(uri: Uri?) {
        _state.value = state.value.copy(imageUri = uri)
    }

    fun setUser(user: FirebaseUser?) {
        _state.value = state.value.copy(user = user)
    }
}

data class State(
    val imageUri: Uri? = null,
    val user: FirebaseUser? = null
)