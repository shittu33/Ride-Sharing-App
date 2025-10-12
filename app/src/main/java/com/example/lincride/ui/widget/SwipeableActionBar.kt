package com.example.lincride.ui.widget

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lincride.R
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

/**
 * Swipeable action bar allowing user to swipe left for "Didn't show" or right for "Picked up"
 */
@Composable
fun SwipeableActionBar(
    onDidntShow: () -> Unit,
    onPickedUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    val scope = rememberCoroutineScope()
    
    // Threshold for triggering actions (20% of width)
    val threshold = 0.2f
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        // Gradient background (red to green)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        0.0f to Color(0xFFFF3932), // Red at 32%
                        0.32f to Color(0xFFFF3932),
                        0.69f to Color(0xFF4A941C), // Green at 69%
                        1.0f to Color(0xFF4A941C)
                    )
                )
        )
        
        // Left action: "Didn't show" with left arrows
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .alpha(if (offsetX < 0) 1f else 0.3f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Double chevron left
            Icon(
                painter = painterResource(id = R.drawable.ic_chevron_double_left),
                contentDescription = null,
                modifier = Modifier.size(42.dp,24.dp),
                tint = Color.White
            )
            
            Text(
                text = "Didn't show",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
        
        // Right action: "Picked up" with right arrows
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .alpha(if (offsetX > 0) 1f else 0.3f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Picked up",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            
            Spacer(modifier = Modifier.width(4.dp))
            
            // Double chevron right
            Icon(
                painter = painterResource(id = R.drawable.ic_chevron_double_right),
                contentDescription = null,
                modifier = Modifier.size(42.dp,24.dp),
                tint = Color.White
            )
        }
        
        // Draggable indicator (invisible but provides swipe area)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            val screenWidth = size.width
                            val swipeThreshold = screenWidth * threshold
                            
                            when {
                                offsetX < -swipeThreshold -> {
                                    // Swiped left - "Didn't show"
                                    scope.launch {
                                        onDidntShow()
                                        offsetX = 0f
                                    }
                                }
                                offsetX > swipeThreshold -> {
                                    // Swiped right - "Picked up"
                                    scope.launch {
                                        onPickedUp()
                                        offsetX = 0f
                                    }
                                }
                                else -> {
                                    // Reset if threshold not reached
                                    scope.launch {
                                        offsetX = 0f
                                    }
                                }
                            }
                        },
                        onHorizontalDrag = { _, dragAmount ->
                            offsetX += dragAmount
                            // Limit the drag distance
                            val maxDrag = size.width * 0.5f
                            offsetX = offsetX.coerceIn(-maxDrag, maxDrag)
                        }
                    )
                }
        )
    }
}
