package com.example.skindisease.domain.use_case

import com.example.skindisease.data.local.DetectionResultDao
import com.example.skindisease.domain.model.DetectionResult

class GetDetectionResult(private val dao: DetectionResultDao) {

    suspend operator fun invoke(id: Int): DetectionResult {
        return dao.getResultById(id)
    }
}