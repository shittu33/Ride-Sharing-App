package com.example.lincride.ui.widget

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.lincride.R
import com.example.lincride.ui.theme.LocalColors
import com.example.lincride.viewModel.RideModeState

@Composable
fun RideModeOptionWidget(
    modifier: Modifier = Modifier,
    selectedOption: RideModeState,
    onRideModeOptionClicked: (RideModeState) -> Unit
) {
    // Ride options
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding().then(modifier),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        RideTypeOption(
            modifier = Modifier.weight(1f).clickable {
                onRideModeOptionClicked(RideModeState.JoinRide)
            },
            icon = R.drawable.img_share_ride,
            title = "Join a Ride",
            description = "Book your seat",
            isSelected = selectedOption == RideModeState.JoinRide
        )
        RideTypeOption(
            modifier = Modifier.weight(1f).clickable {
                onRideModeOptionClicked(RideModeState.OfferRideState)
            },
            icon = R.drawable.img_join_ride,
            title = "Offer Ride",
            description = "Share your trip",
            isSelected = selectedOption == RideModeState.OfferRideState
        )
    }
}


@Composable
private fun RideTypeOption(
    modifier: Modifier = Modifier,
    icon: Int,
    title: String,
    description: String,
    isSelected: Boolean
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                LocalColors.current.primary
            else
                MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.width(34.dp).height(32.dp),
                painter = painterResource(id = icon),
                contentScale = ContentScale.Crop,
                contentDescription = title,
            )
//            Spacer(modifier = Modifier.width(6.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = if(isSelected) LocalColors.current.surface else LocalColors.current.textPrimary
                    )
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = if(isSelected) LocalColors.current.surfaceVariant else LocalColors.current.textSecondary
                    )
                )
            }
        }
    }
}
