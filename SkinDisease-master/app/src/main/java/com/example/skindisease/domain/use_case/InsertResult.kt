package com.example.skindisease.domain.use_case

import com.example.skindisease.data.local.DetectionResultDao
import com.example.skindisease.domain.model.DetectionResult

class InsertResult(private val dao: DetectionResultDao) {

    suspend operator fun invoke(data: DetectionResult) {
        dao.insertResult(data)
    }
}