package com.example.skindisease.ui.screen.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.skindisease.R
import com.example.skindisease.ui.component.Loading
import com.example.skindisease.ui.navhost.MainRoutes
import com.google.firebase.auth.FirebaseUser
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    setUser: (FirebaseUser?) -> Unit
) {
    val viewModel = koinViewModel<LoginViewModel>()
    val state = viewModel.state.value

    if (state.user != null) {
        setUser(state.user)
        LaunchedEffect(Unit) {
            navController.popBackStack()
            navController.navigate(MainRoutes.BottomNavScreen)
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIosNew,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(100.dp))
            Text(
                stringResource(R.string.login_to_your_account),
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                value = state.email,
                onValueChange = { viewModel.changeInput(0, it) },
                label = { Text(stringResource(R.string.email)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                value = state.password,
                onValueChange = { viewModel.changeInput(1, it) },
                label = { Text(stringResource(R.string.password)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (state.showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { viewModel.toggleShowPassword() }) {
                        Icon(
                            imageVector = if (state.showPassword) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                            contentDescription = null
                        )
                    }
                }
            )

            Spacer(Modifier.height(10.dp))

            TextButton(modifier = Modifier.align(Alignment.End), onClick = {
                navController.navigate(MainRoutes.ForgotPasswordScreen)
            }) {
                Text(stringResource(R.string.forgot_password) + "?")
            }

            Spacer(Modifier.height(10.dp))

            if (state.loading) {
                Loading(
                    loading = true, modifier = Modifier
                        .size(50.dp)
                        .align(CenterHorizontally)
                )
            } else {
                Button(
                    onClick = {
                        viewModel.login()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD2C0EA),
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(stringResource(R.string.login))
                }
            }

            Spacer(Modifier.height(15.dp))
            TextButton(
                onClick = {
                    navController.popBackStack()
                    navController.navigate(MainRoutes.RegisterScreen)
                },
                modifier = Modifier.align(CenterHorizontally)
            ) {
                Text(stringResource(R.string.don_t_you_have_an_account))
            }
        }
    }
}