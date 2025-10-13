package com.example.lincride.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lincride.R
import com.example.lincride.ui.theme.LocalColors
import com.example.lincride.utils.NetworkResult
import com.example.lincride.viewModel.RiderViewModel

@Composable
fun ProfileScreen(
    viewModel: RiderViewModel
) {
    val riderInfoState by viewModel.riderInfoState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchRiderInfo()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.user_avatar),
            contentDescription = "Profile Photo",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )
        
        Text(
            text = "John Doe",
            style = MaterialTheme.typography.headlineMedium,
            color = LocalColors.current.textPrimary,
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(Modifier.height(48.dp))

        // State display area
        NetworkResultDisplay(result = riderInfoState)

        Spacer(Modifier.height(24.dp))

        // Get Rider Info button
        Button(
            onClick = { viewModel.fetchRiderInfo() },
            modifier = Modifier.fillMaxWidth(),
            enabled = riderInfoState !is NetworkResult.Loading
        ) {
            Text("Get Rider Info")
        }

        Spacer(Modifier.height(8.dp))

        // Network test instruction
        Text(
            text = "Turn off cellular data or Wifi to test Network Error",
            style = MaterialTheme.typography.bodySmall,
            color = LocalColors.current.textSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun NetworkResultDisplay(result: NetworkResult<String>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = when (result) {
                    is NetworkResult.Loading -> Color(0xFFF5F5F5)
                    is NetworkResult.Success -> Color(0xFFE8F5E9)
                    is NetworkResult.Error -> Color(0xFFFFEBEE)
                },
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        when (result) {
            is NetworkResult.Loading -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(32.dp),
                        color = LocalColors.current.primary
                    )
                    Text(
                        text = "Loading...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = LocalColors.current.textSecondary
                    )
                }
            }

            is NetworkResult.Success -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Success",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF2E7D32)
                    )
                    Text(
                        text = result.data,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF2E7D32),
                        textAlign = TextAlign.Center
                    )
                }
            }

            is NetworkResult.Error.NetworkError -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Network Error",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFFC62828)
                    )
                    Text(
                        text = result.message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFC62828),
                        textAlign = TextAlign.Center
                    )
                }
            }

            else -> {
                Box{}
            }
        }
    }
}
