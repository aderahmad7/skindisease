package com.example.skindisease.domain.remote.repository

import com.example.skindisease.data.remote.model.DetectionResultDto
import com.example.skindisease.data.remote.model.ResponseDefault
import java.io.File

interface AiRepository {

    suspend fun detectSkin(file: File, email: String): ResponseDefault<DetectionResultDto>
}