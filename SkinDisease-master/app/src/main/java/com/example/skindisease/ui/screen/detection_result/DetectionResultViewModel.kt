package com.example.skindisease.ui.screen.detection_result

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skindisease.domain.use_case.UseCase
import kotlinx.coroutines.launch

class DetectionResultViewModel(private val useCase: UseCase) : ViewModel() {

    private val _state = mutableStateOf(DetectionResultContract.State())
    val state: State<DetectionResultContract.State> = _state

    fun getDetectionResult(id: Int) {
        viewModelScope.launch {
            val result = useCase.getDetectionResultById(id)
            _state.value = state.value.copy(result = result)
        }
    }
}