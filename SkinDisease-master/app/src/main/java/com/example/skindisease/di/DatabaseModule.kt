package com.example.skindisease.di

import com.example.skindisease.data.local.SkinDiseaseDb
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        SkinDiseaseDb.getInstance(androidContext())
    }
}