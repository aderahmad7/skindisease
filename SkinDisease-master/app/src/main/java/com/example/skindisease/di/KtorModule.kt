package com.example.skindisease.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.serialization.gson.gson
import org.koin.dsl.module

val ktorModule = module {
    factory<HttpClient> {
        HttpClient(CIO) {
            install(Resources)
            install(HttpTimeout) {
                requestTimeoutMillis = 60000
            }
            install(ContentNegotiation) {
                gson()
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
            defaultRequest {
                url("https://api-dot-belajar-gcloud-6969.et.r.appspot.com/")
            }
        }
    }
}