package com.example.skindisease.data.remote.repository

import com.example.skindisease.data.remote.model.DetectionResultDto
import com.example.skindisease.data.remote.model.ResponseDefault
import com.example.skindisease.data.remote.route.AiRoute
import com.example.skindisease.domain.remote.repository.AiRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import java.io.File

class AiRepositoryImpl(private val client: HttpClient) : AiRepository {
    override suspend fun detectSkin(
        file: File,
        email: String
    ): ResponseDefault<DetectionResultDto> {
        val response: ResponseDefault<DetectionResultDto> = client.post(AiRoute.Predict.path) {
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append("email", email)
                        append("image", file.readBytes(), Headers.build {
                            append(HttpHeaders.ContentType, "image/jpg")
                            append(HttpHeaders.ContentDisposition, "filename=${file.name}")
                        })
                    },
                    boundary = "WebAppBoundary"
                )
            )
        }.body()

        return response
    }
}