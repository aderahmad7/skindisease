package com.example.skindisease.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.skindisease.R
import com.example.skindisease.ui.navhost.MainRoutes
import com.google.firebase.appcheck.interop.BuildConfig
import com.google.firebase.auth.FirebaseUser
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    firebaseUser: FirebaseUser?,
    setUser: (FirebaseUser?) -> Unit,
    mainNavController: NavHostController
) {
    val viewModel = koinViewModel<ProfileViewModel>()
    val state = viewModel.state.value

    val version = BuildConfig.VERSION_NAME

    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = firebaseUser?.photoUrl ?: R.drawable.profile_image,
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(16.dp))
            Column {
                firebaseUser?.let {
                    Text(it.displayName!!, style = MaterialTheme.typography.titleMedium)
                    Text(it.email!!, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        Spacer(Modifier.height(32.dp))
        Text("Account", style = MaterialTheme.typography.titleMedium)
        ListItem(
            modifier = Modifier.clickable {
                mainNavController.navigate(MainRoutes.EditProfileScreen)
            },
            headlineContent = { Text(stringResource(R.string.edit_profile)) },
            leadingContent = {
                Icon(
                    imageVector = Icons.Outlined.AccountCircle,
                    contentDescription = null
                )
            },
            trailingContent = {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowForwardIos,
                    contentDescription = null
                )
            }
        )

        Spacer(Modifier.height(32.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            shape = MaterialTheme.shapes.medium,
            onClick = viewModel::toggleConfirmDialog
        ) {
            Text(stringResource(R.string.logout))
        }
        Text(
            "App version: $version",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        if (state.showConfirmDialog) {
            BasicAlertDialog(
                onDismissRequest = viewModel::toggleConfirmDialog,
                modifier = Modifier.background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.medium
                ),
            ) {
                Column(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(stringResource(R.string.are_you_sure))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = viewModel::toggleConfirmDialog,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Text("No")
                        }
                        Button(onClick = {
                            viewModel.logout()
                            setUser(null)
                        }) {
                            Text("Yes")
                        }
                    }
                }
            }
        }
    }
}