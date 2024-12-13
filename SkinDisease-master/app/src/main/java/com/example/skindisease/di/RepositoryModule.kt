package com.example.skindisease.di

import com.example.skindisease.data.remote.repository.AiRepositoryImpl
import com.example.skindisease.domain.remote.repository.AiRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<AiRepository> {
        AiRepositoryImpl(get())
    }
}