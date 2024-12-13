package com.example.skindisease.di

import com.example.skindisease.domain.use_case.UseCase
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import org.koin.dsl.module

val useCaseModule = module {
    single {
        UseCase(get(), Firebase.auth, get())
    }
}