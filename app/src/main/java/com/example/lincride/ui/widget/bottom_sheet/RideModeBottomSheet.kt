package com.example.lincride.ui.widget.bottom_sheet

import LincDragHandle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.example.lincride.MainActivity
import com.example.lincride.R
import com.example.lincride.ui.theme.LincColors
import com.example.lincride.ui.widget.DestinationTextInput
import com.example.lincride.ui.widget.RideModeOptionWidget
import com.example.lincride.viewModel.RideSimulationViewModel
import com.example.lincride.viewModel.RideState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RideModeBottomSheet(
    viewModel: RideSimulationViewModel
) {
    val rideState by viewModel.rideState.collectAsState()

    val rideModeState by viewModel.rideModeState.collectAsState()

    Box(
        Modifier
            .fillMaxWidth()
            .background(
                color = LincColors.surface
            )
    ) {
        Column {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 12.dp)
                    .padding(top = 8.dp, bottom = 32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Text(
                    text = "Choose your ride mode",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        textAlign = TextAlign.Center,
                        color = LincColors.textPrimary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 2.dp, top = 6.dp)
                )

                //        Spacer(modifier = Modifier.height(1.dp))

                RideModeOptionWidget(
                    modifier = Modifier
                        .padding()
                        .padding(horizontal = 16.dp),
                    selectedOption = rideModeState,
                    onRideModeOptionClicked = viewModel::toggleRideMode
                )

                DestinationTextInput(
                    modifier = Modifier
                        .padding(top = 2.dp, start = 16.dp, end = 16.dp)
                )

            }
        }
    }
}
