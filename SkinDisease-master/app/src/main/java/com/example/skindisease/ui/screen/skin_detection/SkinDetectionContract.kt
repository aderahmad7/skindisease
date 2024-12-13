package com.example.skindisease.ui.screen.skin_detection

import com.example.skindisease.domain.model.DetectionResult
import java.io.File

class SkinDetectionContract {
    data class State(
        val showBottomSheet: Boolean = false,
        val skipPartiallyExpanded: Boolean = false,
        val loading: Boolean = false,
        val showDetectButton: Boolean = false,
        val detectionResult: DetectionResult? = null,
        val redirect: Boolean = false,
        val file: File? = null,
        val error: String = "",
        val success: String = ""
    )
}