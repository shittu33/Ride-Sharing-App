package com.example.lincride.ui.widget

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EarningsListWidget(
    netEarnings: String,
    bonus: String,
    commission: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        // Net Earnings (bold)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Net Earnings",
                style = MaterialTheme.typography.labelLarge.copy(fontSize = 14.sp),
                color = Color(0xFF383838)
            )
            Text(
                text = netEarnings,
                style = MaterialTheme.typography.titleSmall.copy(fontSize = 14.sp),
                color = Color(0xFF383838)
            )
        }
        
        // Bonus
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Bonus",
                style = MaterialTheme.typography.labelLarge.copy(fontSize = 14.sp),
                color = Color(0xFF383838)
            )
            Text(
                text = bonus,
                style = MaterialTheme.typography.labelLarge.copy(fontSize = 14.sp),
                color = Color(0xFF383838)
            )
        }
        
        // Linc Commission
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Linc Commission",
                style = MaterialTheme.typography.labelLarge.copy(fontSize = 14.sp),
                color = Color(0xFF383838)
            )
            Text(
                text = commission,
                style = MaterialTheme.typography.labelLarge.copy(fontSize = 14.sp),
                color = Color(0xFF383838)
            )
        }
    }
}
