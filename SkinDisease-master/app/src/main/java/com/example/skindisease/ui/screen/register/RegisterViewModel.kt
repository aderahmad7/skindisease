package com.example.skindisease.ui.screen.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skindisease.domain.use_case.UseCase
import com.example.skindisease.utils.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RegisterViewModel(
    private val useCase: UseCase
) : ViewModel() {
    private val _state = mutableStateOf(RegisterContract.State())
    val state: State<RegisterContract.State> = _state

    fun changeInput(position: Int, value: TextFieldValue) {
        _state.value = state.value.copy(
            fullName = if (position == 0) value else state.value.fullName,
            email = if (position == 1) value else state.value.email,
            password = if (position == 2) value else state.value.password
        )
    }

    fun toggleShowPassword() {
        _state.value = state.value.copy(showPassword = !state.value.showPassword)
    }

    fun toggleAgree(value: Boolean) {
        _state.value = state.value.copy(isAgree = value)
    }

    fun register() {
        if (!state.value.isAgree) {
            return
        }
        useCase.register(
            state.value.email.text,
            state.value.password.text,
            state.value.fullName.text
        ).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _state.value = state.value.copy(loading = true)
                }

                is Resource.Success -> {
                    _state.value = state.value.copy(loading = false, user = resource.data)
                }

                is Resource.Error -> {
                    _state.value = state.value.copy(loading = false, error = resource.message!!)
                }
            }
        }.launchIn(viewModelScope)
    }
}