package com.example.skindisease.data.remote.model

import com.example.skindisease.domain.model.DetectionResult
import com.google.gson.annotations.SerializedName
import java.io.File

data class DetectionResultDto(

    @field:SerializedName("result")
    val result: String? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("confidenceScore")
    val confidenceScore: Any? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("email")
    val email: String? = null
)

fun DetectionResultDto.toDomain(file: File): DetectionResult {
    return DetectionResult(
        uri = "file://$file",
        name = result ?: "Unknown",
        description = description ?: "Unknown"
    )
}
