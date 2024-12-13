package com.example.skindisease.domain.use_case

import com.example.skindisease.data.local.DetectionResultDao
import com.example.skindisease.domain.model.DetectionResult
import kotlinx.coroutines.flow.Flow

class GetDetectionResults(
    private val dao: DetectionResultDao
) {
    operator fun invoke(limit: Int): Flow<List<DetectionResult>> {
        return dao.getAllResult(limit = limit)
    }
}