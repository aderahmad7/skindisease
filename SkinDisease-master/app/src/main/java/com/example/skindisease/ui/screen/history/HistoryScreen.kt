package com.example.skindisease.ui.screen.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.skindisease.ui.navhost.MainRoutes
import com.example.skindisease.ui.screen.component.NothingToShow
import com.example.skindisease.ui.screen.home.HistoryItem
import com.example.skindisease.ui.theme.topBarColor
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val viewModel = koinViewModel<HistoryViewModel>()
    val state = viewModel.state.value
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Outlined.ArrowBackIosNew, contentDescription = null)
                    }
                },
                title = { Text("History") },
                colors = topBarColor
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            NothingToShow(show = state.history.isEmpty())
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                items(state.history, key = { it.id }) {
                    HistoryItem(
                        modifier = Modifier,
                        data = it,
                        onClick = {
                            scope.launch {
                                navController.navigate(MainRoutes.DetectionResultScreen(it.id))
                            }
                        })
                }
                item {
                    Spacer(Modifier.height(100.dp))
                }
            }
        }
    }
}