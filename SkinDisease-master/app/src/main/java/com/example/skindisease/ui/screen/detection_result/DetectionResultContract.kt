package com.example.skindisease.ui.screen.detection_result

import com.example.skindisease.domain.model.DetectionResult

class DetectionResultContract {
    data class State(
        val result: DetectionResult? = null
    )
}