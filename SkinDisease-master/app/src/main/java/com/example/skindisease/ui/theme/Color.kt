package com.example.skindisease.ui.theme

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarColors
import androidx.compose.ui.graphics.Color

val yellow: Color = Color(0xFFD3FD52)
val black: Color = Color(0xFF000000)

@OptIn(ExperimentalMaterial3Api::class)
val topBarColor = TopAppBarColors(
    containerColor = yellow,
    titleContentColor = black,
    scrolledContainerColor = yellow,
    navigationIconContentColor = black,
    actionIconContentColor = black
)