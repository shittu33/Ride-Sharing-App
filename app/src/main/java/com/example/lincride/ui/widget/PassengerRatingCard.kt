package com.example.lincride.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lincride.R
import com.example.lincride.ui.theme.LincColors

@Composable
fun PassengerRatingCard(
    name: String,
    avatarRes: Int,
    pickupPoint: String,
    onRateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar (36dp)
        Image(
            painter = painterResource(id = avatarRes),
            contentDescription = "Passenger avatar",
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        
        // Name and location info
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
                    text = name,
                    style = MaterialTheme.typography.titleSmall.copy(fontSize = 14.sp),
                    color = Color(0xFF2A2A2A)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_verify),
                    contentDescription = "Verified",
                    modifier = Modifier.size(14.dp),
                    tint = Color.Unspecified
                )
            }
            
            // Pickup point
            Row(
                modifier = Modifier
                    .background(
                        color = Color(0xFFF8F8F8),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 0.dp, vertical = 0.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_location),
                    contentDescription = "Location",
                    modifier = Modifier.size(12.dp),
                    tint = Color.Unspecified
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = "Pick-up Point: ",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                        color = Color(0xFF656565)
                    )
                    Text(
                        text = pickupPoint,
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                        color = Color(0xFF2A2A2A)
                    )
                }
            }
        }
        
        // Rate now button (tiny)
        Button(
            onClick = onRateClick,
            modifier = Modifier
                .height(32.dp)
                .wrapContentWidth(),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF8F8F8),
                contentColor = Color(0xFF2A2A2A)
            ),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 7.dp)
        ) {
            Text(
                text = "Rate now",
                style = MaterialTheme.typography.labelMedium.copy(fontSize = 12.sp),
                color = Color(0xFF2A2A2A)
            )
        }
    }
}
