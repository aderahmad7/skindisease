package com.example.skindisease.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skindisease.domain.use_case.UseCase
import kotlinx.coroutines.launch

class HomeViewModel(private val useCase: UseCase) : ViewModel() {
    private val _state = mutableStateOf(HomeContract.State())
    val state: State<HomeContract.State> = _state

    private fun getResult() {
        viewModelScope.launch {
            useCase.getDetectionResult(2).collect({ result ->
                _state.value = state.value.copy(
                    history = result
                )
            })
        }
    }

    init {
        getResult()
    }
}