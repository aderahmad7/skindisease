package com.example.skindisease.ui.screen.forgot_password

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
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
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.skindisease.R
import com.example.skindisease.ui.component.Loading
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val viewModel = koinViewModel<ForgotPasswordViewModel>()
    val state = viewModel.state.value
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("SkinDisease") },
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
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(60.dp))
            if (state.success) {
                ForgotPasswordSuccess(viewModel = viewModel, navController = navController)
            } else {
                ForgotPasswordForm(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun ForgotPasswordForm(modifier: Modifier = Modifier, viewModel: ForgotPasswordViewModel) {
    val state = viewModel.state.value

    Column(modifier = modifier) {
        Image(
            modifier = Modifier
                .size(250.dp)
                .align(CenterHorizontally),
            painter = painterResource(R.drawable.forgot_password),
            contentDescription = null
        )

        Text(
            stringResource(R.string.forgot_password) + "?",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )

        Text(
            stringResource(R.string.don_t_worry_it_happens),
            style = MaterialTheme.typography.bodyMedium
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            value = state.email,
            onValueChange = { viewModel.updateTextField(it, 0) },
            label = { Text(stringResource(R.string.email)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(Modifier.height(10.dp))

        Loading(
            loading = state.loading,
            modifier = Modifier
                .size(50.dp)
                .align(CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (state.loading.not()) {
            Button(
                onClick = {
                    viewModel.resetPassword()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD2C0EA),
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(stringResource(R.string.send_password_reset_request))
            }
        }
    }
}

@Composable
fun ForgotPasswordSuccess(
    modifier: Modifier = Modifier,
    viewModel: ForgotPasswordViewModel,
    navController: NavHostController
) {
    val state = viewModel.state.value

    Column(modifier = modifier) {
        Image(
            modifier = Modifier
                .size(250.dp)
                .align(CenterHorizontally),
            painter = painterResource(R.drawable.forgot_password_success),
            contentDescription = null
        )

        Text(
            modifier = Modifier.align(CenterHorizontally),
            text = "Request Sent!",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )

        Text(
            modifier = Modifier.align(CenterHorizontally),
            text = stringResource(R.string.kindly_check_your_email),
            style = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center)
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = {
                navController.navigateUp()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD2C0EA),
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(stringResource(R.string.back_to_login))
        }

        TextButton(onClick = {
            viewModel.resetPassword()
        }, modifier = Modifier.align(CenterHorizontally)) {
            Text(stringResource(R.string.didn_t_receive_an_email_resend))
        }
    }
}