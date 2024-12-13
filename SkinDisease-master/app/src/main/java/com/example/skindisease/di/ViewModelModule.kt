package com.example.skindisease.di

import com.example.skindisease.MainViewModel
import com.example.skindisease.ui.screen.bottom_nav.BottomNavViewModel
import com.example.skindisease.ui.screen.detection_result.DetectionResultViewModel
import com.example.skindisease.ui.screen.edit_profile.EditProfileViewModel
import com.example.skindisease.ui.screen.forgot_password.ForgotPasswordViewModel
import com.example.skindisease.ui.screen.history.HistoryViewModel
import com.example.skindisease.ui.screen.home.HomeViewModel
import com.example.skindisease.ui.screen.login.LoginViewModel
import com.example.skindisease.ui.screen.profile.ProfileViewModel
import com.example.skindisease.ui.screen.register.RegisterViewModel
import com.example.skindisease.ui.screen.skin_detection.SkinDetectionViewModel
import com.example.skindisease.ui.screen.splash.SplashViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SkinDetectionViewModel(get()) }
    viewModel { ForgotPasswordViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { BottomNavViewModel() }
    viewModel { MainViewModel() }
    viewModel { SplashViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { HistoryViewModel(get()) }
    viewModel { DetectionResultViewModel(get()) }
    viewModel { EditProfileViewModel(get()) }
}