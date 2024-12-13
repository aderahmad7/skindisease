package com.example.skindisease.data.remote.route

sealed class AiRoute(val path: String) {
    data object Predict : AiRoute("predict")
}