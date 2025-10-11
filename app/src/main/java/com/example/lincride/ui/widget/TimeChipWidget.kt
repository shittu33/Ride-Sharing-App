package com.example.lincride.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.lincride.R
import com.example.lincride.ui.theme.LincColors

@Composable
fun TimeChipWidget(
    timeText: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(
                color = Color(0xFFEAF1FF),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(36.dp)
            )
            .padding(horizontal = 8.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_clock),
            contentDescription = null,
            modifier = Modifier.size(14.dp),
            tint = LincColors.primary
        )
        Text(
            text = timeText,
            style = androidx.compose.material3.MaterialTheme.typography.labelMedium,
            color = LincColors.textPrimary
        )
    }
}
