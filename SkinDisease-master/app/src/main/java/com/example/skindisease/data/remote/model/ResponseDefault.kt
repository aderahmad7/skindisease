package com.example.skindisease.data.remote.model

import com.google.gson.annotations.SerializedName

data class ResponseDefault<T>(

    @field:SerializedName("data")
    val data: T? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)
