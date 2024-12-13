package com.example.skindisease.ui.screen.get_started

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.skindisease.R
import com.example.skindisease.ui.navhost.MainRoutes

@Composable
fun GetStartedScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    var clicked by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.clickable {
            clicked = true
        }
    ) {
        AnimatedVisibility(
            visible = !clicked,
            exit = fadeOut(animationSpec = tween(durationMillis = 3000)),
            enter = fadeIn()
        ) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(R.drawable.frame_get_started),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(30.dp))
            if (!clicked) {
                Text(
                    stringResource(R.string.getting_started),
                    modifier = Modifier.align(CenterHorizontally),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = Color(0xFFD2C0EA),
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Spacer(Modifier.height(80.dp))
            Image(
                modifier = Modifier
                    .size(300.dp)
                    .align(CenterHorizontally),
                painter = painterResource(R.drawable.people_get_started),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
            Spacer(Modifier.height(30.dp))
            Column(
                modifier = Modifier
                    .padding(horizontal = 30.dp),
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(R.string.get_started_subtitle1),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(R.string.get_started_subtitle2),
                    style = MaterialTheme.typography.bodySmall.copy(textAlign = TextAlign.Center)
                )
            }
            Spacer(Modifier.height(30.dp))
            if (clicked) {
                Button(
                    onClick = {
                        navController.navigate(MainRoutes.LoginScreen)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD2C0EA),
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(stringResource(R.string.login))
                }
                Button(
                    onClick = {
                        navController.navigate(MainRoutes.RegisterScreen)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth(),
                    border = BorderStroke(1.dp, Color.Black),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(stringResource(R.string.i_m_new_sign_me_up))
                }
            }
        }
    }
}