package com.example.skindisease.ui.screen.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.skindisease.R
import com.example.skindisease.ui.component.Animation

@Composable
fun NothingToShow(
    modifier: Modifier = Modifier,
    show: Boolean
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation_1732192734016))
    if (show) {
        Column(modifier = modifier.fillMaxWidth()) {
            Spacer(Modifier.height(30.dp))
            Animation(
                modifier = Modifier.align(CenterHorizontally),
                composition = composition,
                loop = false
            )
            Text(
                "Nothing to show",
                modifier = Modifier
                    .size(150.dp)
                    .align(CenterHorizontally)
            )
        }
    }
}