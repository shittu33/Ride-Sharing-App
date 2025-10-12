package com.example.lincride.ui.screen

import android.util.Log
import android.util.LogPrinter
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.lincride.ui.widget.LincBottomNavigationBar
import com.example.lincride.viewModel.RideSimulationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: RideSimulationViewModel,
    content: @Composable () -> Unit
) {
    var currentRoute by rememberSaveable { mutableStateOf("home") }

    Box(Modifier) {
        Scaffold(modifier = Modifier.fillMaxSize(),
            contentColor = Color.Transparent,
            containerColor = Color.Transparent,

            bottomBar = {
                LincBottomNavigationBar(currentRoute = currentRoute, onNavigate = { route ->
                    Log.d("MainScreen", "onNavigate: $route")
                    navController.navigate(route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo("home") {
                            saveState = true
                        }
                    }
                    currentRoute = route
                })
            }) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(paddingValues)
            ) {
                content()
            }
        }
        // Trip Ended Overlay (appears on top of everything)
        TripEndedOverlay(visible = rideState is RideState.TripEnded, onNewTrip = {
            viewModel.resetSimulation()
        }, onEarningsHistory = {
            navController.navigate("history") {
                launchSingleTop = true
                restoreState = true
                popUpTo("home") {
                    saveState = true
                }
            }
        }, onCancel = {
            viewModel.resetSimulation()
        })
    }
}
