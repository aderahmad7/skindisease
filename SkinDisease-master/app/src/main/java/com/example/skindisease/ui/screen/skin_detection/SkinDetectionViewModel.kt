package com.example.skindisease.ui.screen.skin_detection

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skindisease.domain.model.DetectionResult
import com.example.skindisease.domain.use_case.UseCase
import com.example.skindisease.utils.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.File

class SkinDetectionViewModel(private val useCase: UseCase) : ViewModel() {
    private val _state = mutableStateOf(SkinDetectionContract.State())
    val state: State<SkinDetectionContract.State> = _state

    fun toggleBottomSheet() {
        _state.value = state.value.copy(showBottomSheet = !state.value.showBottomSheet)
    }

    fun showDetectButton(show: Boolean) {
        _state.value = state.value.copy(showDetectButton = show)
    }

    fun setDetectionResult(result: DetectionResult?) {
        _state.value = state.value.copy(detectionResult = result)
    }

    private fun saveResult(result: DetectionResult) {
        viewModelScope.launch {
            useCase.insertResult(result)
            getLatestResult()
        }
    }

    private fun getLatestResult() {
        viewModelScope.launch {
            val result = useCase.getLatestResult()
            setDetectionResult(result)
        }
    }

    fun detect(email: String) {
        showDetectButton(false)
        _state.value = state.value.copy(detectionResult = null)
        useCase.predict(image = state.value.file!!, email = email).onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    saveResult(resource.data!!)
                    _state.value = state.value.copy(loading = false)
                }

                is Resource.Error -> {
                    _state.value = state.value.copy(loading = false, error = resource.message!!)
                }

                is Resource.Loading -> {
                    _state.value = state.value.copy(loading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun setFile(file: File?) {
        _state.value = state.value.copy(file = file)
    }
}