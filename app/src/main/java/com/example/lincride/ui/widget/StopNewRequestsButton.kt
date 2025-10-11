package com.example.lincride.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lincride.R

/**
 * Stop new requests button - displayed at the top center of the map
 * during active ride states (e.g., DrivingToPickup)
 */
@Composable
fun StopNewRequestsButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(128.dp),
                ambientColor = Color.Black.copy(alpha = 0.06f),
                spotColor = Color.Black.copy(alpha = 0.1f)
            )
            .background(
                color = Color(0xFF2A2A2A), // #2A2A2A from Figma
                shape = RoundedCornerShape(128.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Close icon (X)
        Icon(
            painter = painterResource(id = R.drawable.ic_close_outline),
            contentDescription = "Close",
            modifier = Modifier.size(17.41.dp),
            tint = Color(0xFFF8F8F8) // #F8F8F8 from Figma
        )

        // Button text
        Text(
            text = "Stop new requests",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 18.sp,
            color = Color(0xFFF8F8F8) // #F8F8F8 from Figma
        )
    }
}
