package com.example.lincride.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lincride.R
import com.example.lincride.ui.theme.LincColors

@Composable
fun RiderInfoWidget(
    riderName: String,
    riderRating: String,
    riderImageRes: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar
        Image(
            painter = painterResource(id = riderImageRes),
            contentDescription = "Rider avatar",
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        
        // Rider info
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            // Name with verification badge
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = riderName,
                    style = MaterialTheme.typography.labelMedium.copy(fontSize = 10.sp),
                    color = LincColors.textPrimary
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_verify),
                    contentDescription = "Verified",
                    modifier = Modifier.size(14.dp),
                    tint = Color.Unspecified
                )
            }
            
            // Rating
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = "Rating",
                    modifier = Modifier.size(10.dp),
                    tint = Color.Unspecified
                )
                Text(
                    text = riderRating,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 10.sp,
                        fontStyle = FontStyle.Italic
                    ),
                    color = LincColors.textSecondary
                )
            }
        }
        
        // Action buttons
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Message button
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        color = LincColors.surfaceVariant,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Box {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_message),
                        contentDescription = "Message",
                        modifier = Modifier.size(16.dp),
                        tint = LincColors.textPrimary
                    )
                }
                Badge(
                    modifier = Modifier.size(10.dp)
                        .align(Alignment.TopEnd)
                        .offset(x = (-1).dp, y = (1).dp),
                    containerColor = Color(0xFFFF4D4D),
                    contentColor = Color.White,
                ){

                }
            }
            
            // Call button
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        color = LincColors.surfaceVariant,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_call),
                    contentDescription = "Call",
                    modifier = Modifier.size(16.dp),
                    tint = LincColors.textPrimary
                )
            }
        }
    }
}
