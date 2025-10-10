package com.example.lincride.ui.widget

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lincride.ui.theme.LocalColors

@Composable
fun LincBottomNavigationBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    val labelStyle = MaterialTheme.typography.labelMedium.copy(fontSize = 11.sp)
    val colors = NavigationBarItemDefaults.colors().copy(
        selectedIndicatorColor = Color.Transparent,
        selectedTextColor = LocalColors.current.textPrimary,
        unselectedTextColor = LocalColors.current.textSecondary
    )
    val navigationItems = NavigationItem.getAllItems()

    NavigationBar(
        Modifier.shadow(10.dp),
        containerColor = LocalColors.current.surface,
        windowInsets = NavigationBarDefaults.windowInsets.only(
            sides = WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
        )
    ) {
        Spacer(modifier = Modifier.width(14.dp))

        navigationItems.forEach { item ->
            val isSelected = currentRoute == item.route
            val iconRes = if (isSelected) item.selectedIcon else item.unselectedIcon

            NavigationBarItem(
                icon = {
                    // Profile uses Image, others use Icon
                    Image(
                        painter = painterResource(id = iconRes),
                        contentDescription = item.label,
                        modifier = Modifier.size(24.dp),
                        contentScale = if (item is NavigationItem.Profile) ContentScale.Crop else ContentScale.None
                    )
                },
                label = { Text(item.label, style = labelStyle) },
                selected = isSelected,
                colors = colors,
                onClick = {
                    Log.d("LincBottomNavigationBar", "onNavigate: ${item.route}")
                    onNavigate(item.route)
                }
            )
        }

        Spacer(modifier = Modifier.width(14.dp))
    }
}
