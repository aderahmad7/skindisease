package com.example.skindisease.ui.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.skindisease.R
import com.example.skindisease.ui.component.Loading
import com.example.skindisease.ui.navhost.MainRoutes
import com.google.firebase.auth.FirebaseUser
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    setUser: (FirebaseUser?) -> Unit
) {
    val viewModel = koinViewModel<SplashViewModel>()
    val state = viewModel.state.value

    if (state.screenReady) {
        setUser(state.user)
        LaunchedEffect(Unit) {
            navController.popBackStack()
            navController.navigate(MainRoutes.BottomNavScreen)
        }
    }

    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(200.dp),
            contentScale = ContentScale.Crop
        )
        Loading(
            loading = state.loading,
            modifier = Modifier
                .padding(bottom = 30.dp)
                .size(50.dp)
                .align(Alignment.BottomCenter)
        )
    }
}