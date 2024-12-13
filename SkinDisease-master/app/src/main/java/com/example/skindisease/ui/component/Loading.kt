package com.example.skindisease.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.skindisease.R

@Composable
fun Loading(modifier: Modifier = Modifier, loading: Boolean) {
    if (loading) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation_1732174684063))
        LottieAnimation(
            modifier = modifier,
            composition = composition,
            reverseOnRepeat = true,
            iterations = LottieConstants.IterateForever
        )
    }
}