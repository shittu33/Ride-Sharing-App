package com.example.lincride.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.lincride.R
import com.example.lincride.ui.theme.LocalColors

@Composable
fun DestinationTextInput(
    modifier: Modifier = Modifier
) {
    var value by remember { mutableStateOf("") }
    OutlinedTextField(
        value = value,
        onValueChange = { value = it },
        modifier = modifier.fillMaxWidth().height(54.dp),
        placeholder = { Text("Where to?", style = MaterialTheme.typography.bodyLarge) },
        leadingIcon = {
            Image(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(LocalColors.current.surfaceVariant)
                    .padding(8.dp),
                painter = painterResource(id = R.drawable.routing),
                contentDescription = "Search"
            )
        },
        shape = RoundedCornerShape(128.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            unfocusedLeadingIconColor = LocalColors.current.textSecondary,
            focusedLeadingIconColor = LocalColors.current.primary,
            focusedContainerColor = LocalColors.current.surfaceVariant,
            unfocusedContainerColor = LocalColors.current.surfaceVariant
        ),
        singleLine = true
    )
}
