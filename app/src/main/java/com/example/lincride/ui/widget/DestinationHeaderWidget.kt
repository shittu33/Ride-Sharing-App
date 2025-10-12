package com.example.lincride.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lincride.ui.theme.LincColors

@Composable
fun DestinationHeaderWidget(
    destinationName: String,
    passengerName: String,
    passengerImageRes: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 0.dp)
            .padding(top = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        // Left section: Title and destination info
        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
        ) {
            Text(
                text = "Heading to",
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp),
                color = LincColors.textPrimary
            )
            Spacer(modifier = Modifier.weight(1f))
            Column(
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = destinationName,
                    style = MaterialTheme.typography.labelMedium.copy(fontSize = 12.sp),
                    color = LincColors.textPrimary, textAlign = TextAlign.End
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.weight(0.1f))
                    Text(
                        text = "To drop off",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontSize = 12.sp,
                            fontStyle = FontStyle.Italic
                        ),
                        color = LincColors.textSecondary
                    )
                    
                    // Passenger avatar
                    Image(
                        painter = painterResource(id = passengerImageRes),
                        contentDescription = "Passenger avatar",
                        modifier = Modifier
                            .size(14.dp)
                            .clip(CircleShape)
                            .border(
                                width = 1.dp,
                                color = Color(0xFFD27B0D),
                                shape = CircleShape
                            ),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}
