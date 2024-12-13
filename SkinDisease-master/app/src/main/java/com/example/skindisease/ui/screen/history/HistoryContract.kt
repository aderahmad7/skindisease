package com.example.skindisease.ui.screen.history

import com.example.skindisease.domain.model.DetectionResult

class HistoryContract {
    data class State(
        val history: List<DetectionResult> = emptyList()
    )
}