package com.example.skindisease.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.skindisease.domain.model.DetectionResult

@Composable
fun HistoryItem(
    modifier: Modifier = Modifier,
    data: DetectionResult,
    onClick: (DetectionResult) -> Unit
) {
    Box(
        modifier = modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .background(color = Color.White, shape = MaterialTheme.shapes.medium)
            .clip(shape = MaterialTheme.shapes.medium)
            .clickable {
                onClick(data)
            }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = data.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                data.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}