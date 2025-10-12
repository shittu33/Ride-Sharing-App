package com.example.lincride.ui.widget

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lincride.ui.theme.LincColors

data class RouteStop(
    val label: String,
    val address: String,
    val labelColor: Color,
    val isItalic: Boolean = false,
    val avatarRes: Int? = null,
    val borderColor: Color? = null
)

@Composable
fun MultiStopRouteWidget(
    startingPoint: String,
    destination: String,
    dropOffStops: List<RouteStop> = emptyList(),
    routeInfo: String? = null,
    etaMinutes: String? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
//            .background(color = Color(0xFFF8F8F8))
            .padding(horizontal = 8.dp).padding(top= 0.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Range indicator column with circles and dashed lines
        Column(
            modifier = Modifier
                .padding(top = 12.dp, bottom = 12.dp)
                .padding(horizontal = 2.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Starting point circle (green)
            Box(
                modifier = Modifier
                    .size(14.dp)
                    .background(
                        color = LincColors.surface,
                        shape = CircleShape
                    )
                    .border(
                        width = 1.dp,
                        color = Color(0xFF2A2A2A),
                        shape = CircleShape
                    )
            )
            
            // Dashed line segments between stops
            dropOffStops.forEachIndexed { index, stop ->
                // Dashed line before each drop-off
                val lineColor = when {
                    stop.labelColor == Color(0xFF4A941C) -> Color(0xFF4A941C) // Green
                    stop.labelColor == Color(0xFFD27B0D) -> Color(0xFFD27B0D) // Orange
                    else -> Color(0xFFB0B0B0) // Gray
                }
                
                Canvas(
                    modifier = Modifier
                        .width(0.dp)
                        .height(if (index < dropOffStops.size - 1) 44.dp else 38.dp)
                ) {
                    drawLine(
                        color = lineColor,
                        start = Offset(0f, 0f),
                        end = Offset(0f, size.height),
                        strokeWidth = 2.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(
                            intervals = floatArrayOf(6.dp.toPx(), 6.dp.toPx())
                        )
                    )
                }

                // Avatar for drop-off stop
                if (stop.avatarRes != null) {
                    Image(
                        painter = painterResource(id = stop.avatarRes),
                        contentDescription = "Passenger avatar",
                        modifier = Modifier
                            .size(14.dp)
                            .clip(CircleShape)
                            .border(
                                width = 1.dp,
                                color = stop.borderColor ?: Color(0xFF60B527),
                                shape = CircleShape
                            ),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            
            // Final dashed line to destination (gray)
            Canvas(
                modifier = Modifier
                    .width(0.dp)
                    .height(44.dp)
            ) {
                drawLine(
                    color = Color(0xFFB0B0B0),
                    start = Offset(0f, 0f),
                    end = Offset(0f, size.height),
                    strokeWidth = 2.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(
                        intervals = floatArrayOf(6.dp.toPx(), 6.dp.toPx())
                    )
                )
            }
            
            // Destination circle (black)
            Box(
                modifier = Modifier
                    .size(14.dp)
                    .background(
                        color = Color(0xFF2A2A2A),
                        shape = CircleShape
                    )
            )
        }
        
        // Landmarks column with addresses
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            // Starting point
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Starting Point",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                    color = Color(0xFF656565),
                    modifier = Modifier.padding(top = 12.dp, bottom = 5.dp)
                )
                Text(
                    text = startingPoint,
                    style = MaterialTheme.typography.labelMedium.copy(fontSize = 12.sp),
                    color = Color(0xFF383838)
                )
            }

            // Drop-off stops
            dropOffStops.forEachIndexed { index, stop ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = if(index == 0) 20.dp else 10.dp)
                        .padding(bottom = if(index == 0) 4.dp else 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = stop.label,
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                            color = stop.labelColor,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = stop.address,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontSize = 12.sp,
                                fontStyle = if (stop.isItalic) FontStyle.Italic else FontStyle.Normal
                            ),
                            color = Color(0xFF383838)
                        )
                    }

                    // Avatar (24dp version for the row)
                    if (stop.avatarRes != null) {
                        Image(
                            painter = painterResource(id = stop.avatarRes),
                            contentDescription = "Passenger avatar",
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .border(
                                    width = 1.dp,
                                    color = stop.borderColor ?: Color(0xFF60B527),
                                    shape = CircleShape
                                ),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Spacer(Modifier.height(10.dp))
                // Divider between stops (except after last stop)
                if (index < dropOffStops.size - 1) {
                    HorizontalDivider(
                        color = Color(0xFFD1D1D1),
                        thickness = 0.5.dp
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            // Destination
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Destination",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                        color = Color(0xFF656565),
                        modifier = Modifier.padding(bottom = 4.dp, top = 2.dp)

                    )
                    Text(
                        text = destination,
                        style = MaterialTheme.typography.labelMedium.copy(fontSize = 12.sp),
                        color = Color(0xFF383838)
                    )
                }
            }

            // Route info chip (if provided)
            if (routeInfo != null && etaMinutes != null) {
                Box(
                    modifier = Modifier
                        .offset(x = 182.dp, y = (-118).dp)
                ) {
                    Row(
                        modifier = Modifier
                            .background(
                                color = Color(0xFFF0F0F0),
                                shape = RoundedCornerShape(6.dp)
                            )
                            .border(
                                width = 0.2.dp,
                                color = Color(0xFFD1D1D1),
                                shape = RoundedCornerShape(6.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = routeInfo,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontSize = 10.sp,
                                fontStyle = FontStyle.Italic
                            ),
                            color = Color(0xFF383838)
                        )
                        Box(
                            modifier = Modifier
                                .size(2.dp)
                                .background(Color(0xFF2A2A2A), CircleShape)
                        )
                        Text(
                            text = etaMinutes,
                            style = MaterialTheme.typography.labelMedium.copy(fontSize = 10.sp),
                            color = Color(0xFF2A2A2A)
                        )
                    }
                }
            }
        }
    }
}
