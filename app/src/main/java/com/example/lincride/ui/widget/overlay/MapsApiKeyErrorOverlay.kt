package com.example.lincride.ui.widget.overlay

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.lincride.ui.theme.LocalColors
import com.example.lincride.utils.MapsApiKeyValidator

/**
 * Full-screen overlay displayed when Google Maps API key is missing or invalid.
 * Provides clear instructions for developers to configure the API key.
 */
@Composable
fun MapsApiKeyErrorOverlay(
    apiKeyStatus: MapsApiKeyValidator.ApiKeyStatus,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color(0xFFF5F5F5)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Title
            Text(
                text = "Google Maps API Key Required",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF212121),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(16.dp))


            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                // Step title
                Text(
                    text = "Add to local.properties",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = LocalColors.current.textPrimary
                )

                // Step description
                Text(
                    text = "Add the following line to your secrets.properties file:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = LocalColors.current.textSecondary
                )

                // Code block if provided
                Text(
                    text = "MAPS_API_KEY=YOUR_API_KEY_HERE",
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = FontFamily.Monospace,
                    color = Color(0xFF4CAF50),
                    modifier = Modifier.padding(12.dp)
                )
            }

            Spacer(Modifier.height(24.dp))

        }
    }
}


