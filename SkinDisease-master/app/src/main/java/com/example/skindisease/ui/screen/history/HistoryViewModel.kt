package com.example.skindisease.ui.screen.history

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skindisease.domain.use_case.UseCase
import kotlinx.coroutines.launch

class HistoryViewModel(private val useCase: UseCase) : ViewModel() {

    private val _state = mutableStateOf(HistoryContract.State())
    val state: State<HistoryContract.State> = _state

    private fun getHistory() {
        viewModelScope.launch {
            useCase.getDetectionResult(100).collect({ result ->
                _state.value = state.value.copy(history = result)
            })
        }
    }

    init {
        getHistory()
    }
}