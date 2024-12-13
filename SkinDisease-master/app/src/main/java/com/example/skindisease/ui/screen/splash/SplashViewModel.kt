package com.example.skindisease.ui.screen.splash

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skindisease.domain.use_case.UseCase
import com.example.skindisease.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SplashViewModel(private val useCase: UseCase) : ViewModel() {
    private val _state = mutableStateOf(SplashContract.State())
    val state: State<SplashContract.State> = _state

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
                            user = resource.data,
                            loading = false,
                            screenReady = true
                        )
                }

                is Resource.Error -> {
                    delay(3000)
                    _state.value = state.value.copy(
                        loading = false,
                        error = resource.message!!,
                        screenReady = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    init {
        checkUser()
    }
}