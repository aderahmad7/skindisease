package com.example.skindisease.ui.screen.forgot_password

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skindisease.domain.use_case.UseCase
import com.example.skindisease.utils.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ForgotPasswordViewModel(
    private val useCase: UseCase
) : ViewModel() {
    private val _state = mutableStateOf(ForgotPasswordContract.State())
    val state: State<ForgotPasswordContract.State> = _state

    fun updateTextField(value: TextFieldValue, textField: Int) {
        when (textField) {
            0 -> _state.value = state.value.copy(email = value)
        }
    }

    fun toggleSuccess() {
        _state.value = state.value.copy(success = !_state.value.success)
    }

    fun resetPassword() {
        useCase.resetPassword(email = state.value.email.text).onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    Log.d(TAG, "resetPassword: Success")
                    _state.value = state.value.copy(
                        loading = false,
                    )
                    toggleSuccess()
                }

                is Resource.Error -> {
                    Log.d(TAG, "resetPassword: Error ${resource.message}")
                    _state.value = state.value.copy(loading = false, error = resource.message!!)
                }

                is Resource.Loading -> {
                    _state.value = state.value.copy(loading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}