package com.example.skindisease.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation

@Composable
fun Animation(
    modifier: Modifier = Modifier,
    composition: LottieComposition?,
    loop: Boolean = false
) {
    LottieAnimation(
        modifier = modifier
            .size(150.dp),
        composition = composition,
        reverseOnRepeat = true,
        iterations = if (loop) Int.MAX_VALUE else 1,
    )
}