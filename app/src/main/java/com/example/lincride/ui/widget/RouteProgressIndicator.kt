package com.example.lincride.ui.widget

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lincride.R
import com.example.lincride.ui.theme.LincColors

@Composable
fun RouteProgressIndicator(
    pickupLabel: String,
    pickupAddress: String,
    etaMinutes: String,
    progress: Float = 0f,
    modifier: Modifier = Modifier,
    hideEta: Boolean = false
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp), color = Color(0xFFEAFFF6))
            .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
            .padding(horizontal = 12.dp).padding(bottom = 6.dp, top = 0.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Progress indicator column
        Image(
            painter = painterResource(id = R.drawable.ic_range),
            contentDescription = "Route progress Icon",
        )
        
        // Location details
        Column(
            modifier = Modifier.weight(1f)
        ) {
            // Pickup point
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = pickupLabel,
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                    color = LincColors.textSecondary
                )
                Text(
                    text = pickupAddress,
                    style = MaterialTheme.typography.labelMedium.copy(fontSize = 12.sp),
                    color = LincColors.textPrimary
                )
            }
        }
        
        // ETA chip (conditionally shown)
        if (!hideEta) {
            Row(
                modifier = Modifier
                    .shadow(1.dp, shape = androidx.compose.foundation.shape.RoundedCornerShape(6.dp))
                    .background(
                        color = Color(0xFFBEFFE2),
                        shape = RoundedCornerShape(6.dp, 6.dp, 0.dp, 0.dp)
                    )
                    .clip(RoundedCornerShape(6.dp, 6.dp, 0.dp, 0.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ETA",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontSize = 10.sp,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    ),
                    color = LincColors.textPrimary
                )
                Box(
                    modifier = Modifier
                        .size(2.dp)
                        .background(LincColors.textPrimary, CircleShape)
                )
                Text(
                    text = etaMinutes,
                    style = MaterialTheme.typography.labelMedium.copy(fontSize = 10.sp),
                    color = LincColors.textPrimary
                )
            }
        }
    }
}
