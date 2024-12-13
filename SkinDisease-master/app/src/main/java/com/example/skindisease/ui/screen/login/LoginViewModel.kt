package com.example.skindisease.ui.screen.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skindisease.domain.use_case.UseCase
import com.example.skindisease.utils.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LoginViewModel(private val useCase: UseCase) : ViewModel() {

    private val _state = mutableStateOf(LoginContract.State())
    val state: State<LoginContract.State> = _state

    fun changeInput(position: Int, value: TextFieldValue) {
        _state.value = state.value.copy(
            email = if (position == 0) value else state.value.email,
            password = if (position == 1) value else state.value.password
        )
    }

    fun toggleShowPassword() {
        _state.value = state.value.copy(showPassword = !state.value.showPassword)
    }

    fun login() {
        useCase.login(state.value.email.text, state.value.password.text).onEach { resource ->
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