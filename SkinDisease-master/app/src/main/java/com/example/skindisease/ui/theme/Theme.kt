package com.example.skindisease.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.materialkolor.rememberDynamicColorScheme

@Composable
fun SkinDiseaseTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = rememberDynamicColorScheme(Color(0xFFD3FD52), false, isAmoled = false)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}