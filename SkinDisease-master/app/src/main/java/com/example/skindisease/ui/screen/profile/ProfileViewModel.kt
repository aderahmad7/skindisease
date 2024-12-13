package com.example.skindisease.ui.screen.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skindisease.domain.use_case.UseCase
import com.example.skindisease.utils.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProfileViewModel(private val useCase: UseCase) : ViewModel() {
    private val _state = mutableStateOf(ProfileContract.State())
    val state: State<ProfileContract.State> = _state

    fun toggleConfirmDialog() {
        _state.value = state.value.copy(showConfirmDialog = !state.value.showConfirmDialog)
    }

    fun logout() {
        useCase.logout().onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _state.value = state.value.copy(loading = false, user = null)
                }

                is Resource.Error -> {
                    _state.value = state.value.copy(loading = false, error = resource.message!!)
                }

                is Resource.Loading -> {
                    toggleConfirmDialog()
                    _state.value = state.value.copy(loading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}