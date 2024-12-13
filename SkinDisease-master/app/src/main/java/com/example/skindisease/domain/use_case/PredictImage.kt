package com.example.skindisease.domain.use_case

import com.example.skindisease.data.remote.model.toDomain
import com.example.skindisease.domain.model.DetectionResult
import com.example.skindisease.domain.remote.repository.AiRepository
import com.example.skindisease.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File

class PredictImage(private val repo: AiRepository) {
    operator fun invoke(image: File, email: String): Flow<Resource<DetectionResult>> = flow {
        emit(Resource.Loading())
        try {
            val response = repo.detectSkin(image, email)
            emit(Resource.Success(response.data!!.toDomain(image)))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}