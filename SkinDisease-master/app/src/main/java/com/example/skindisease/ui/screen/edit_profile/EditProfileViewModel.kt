package com.example.skindisease.ui.screen.edit_profile

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skindisease.domain.use_case.UseCase
import com.example.skindisease.utils.Resource
import com.example.skindisease.utils.toUri
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class EditProfileViewModel(private val useCase: UseCase) :
    ViewModel() {

    private val _state = mutableStateOf(EditProfileState())
    val state: State<EditProfileState> = _state

    private fun checkUser() {
        useCase.getUser().onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _state.value = state.value.copy(loading = true)
                }

                is Resource.Success -> {
                    delay(3000)
                    _state.value =
                        state.value.copy(
                            loading = false,
                        )
                    prefillInputs(resource.data)
                }

                is Resource.Error -> {
                    delay(3000)
                    _state.value = state.value.copy(
                        loading = false,
                        error = resource.message!!,
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun prefillInputs(user: FirebaseUser?) {
        _state.value = state.value.copy(
            fullName = TextFieldValue(user?.displayName ?: ""),
            email = TextFieldValue(user?.email ?: ""),
            imageUrl = user?.photoUrl
        )
    }

    fun changeInput(index: Int, value: TextFieldValue) {
        when (index) {
            0 -> {
                _state.value = state.value.copy(fullName = value)
            }

            1 -> {
                _state.value = state.value.copy(email = value)
            }
        }
    }

    fun setImageUri(uri: String) {
        _state.value = state.value.copy(
            imageUrl = uri.toUri()
        )
    }

    fun updateUser() {
        val newProfile = UserProfileChangeRequest.Builder()
            .setDisplayName(state.value.fullName.text)
            .setPhotoUri(state.value.imageUrl)
            .build()

        useCase.updateProfile(newProfile).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _state.value = state.value.copy(
                        loading = true
                    )
                }

                is Resource.Success -> {
                    _state.value = state.value.copy(
                        loading = false,
                        success = "Profile updated successfully"
                    )
                }

                is Resource.Error -> {
                    _state.value = state.value.copy(
                        loading = false,
                        error = resource.message!!
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    init {
        checkUser()
    }
}