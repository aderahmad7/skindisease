package com.example.skindisease.ui.screen.home

import com.example.skindisease.domain.model.DetectionResult

class HomeContract {

    data class State(
        val loading: Boolean = false,
        val history: List<DetectionResult> = emptyList()
    )
}