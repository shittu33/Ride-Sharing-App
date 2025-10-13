package com.example.lincride

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lincride.api.FakeRideService
import com.example.lincride.repositories.FakeRideRepository
import com.example.lincride.ui.screen.HistoryScreen
import com.example.lincride.ui.screen.HomeScreen
import com.example.lincride.ui.screen.MainScreen
import com.example.lincride.ui.screen.ProfileScreen
import com.example.lincride.ui.theme.LincTheme
import com.example.lincride.utils.AndroidNetworkChecker
import com.example.lincride.utils.NetworkChecker
import com.example.lincride.viewModel.RideSimulationViewModel
import com.example.lincride.viewModel.RiderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val rideSimulationViewModel: RideSimulationViewModel by viewModels()
    val riderViewModel: RiderViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            SystemBarStyle.auto(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            )
        )
        window.makeStatusBarTransparent()

        setContent {
            LincTheme {
                LincRideMainScreen(rideSimulationViewModel,riderViewModel, rememberNavController())
            }
        }

    }
}

@Composable
fun LincRideMainScreen(
    viewModel: RideSimulationViewModel,
    riderViewModel: RiderViewModel,
    navController: NavHostController
) {

    MainScreen(navController = navController, viewModel) {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                HomeScreen(viewModel)
            }
            composable("history") {
                HistoryScreen(riderViewModel)
            }
            composable("profile") {
                ProfileScreen(riderViewModel)
            }
        }
    }
}


@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun LincRidePreview(modifier: Modifier = Modifier) {

    val context = LocalContext.current

    LincTheme {
        // A surface container using the 'background' color from the theme
        LincRideMainScreen(
            viewModel = RideSimulationViewModel(),
            riderViewModel = RiderViewModel(FakeRideRepository(
                FakeRideService(),
                networkChecker = AndroidNetworkChecker(context)
            )),
            navController = rememberNavController()
        )
    }
}

private fun Window.makeStatusBarTransparent() {
    setFlags(
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    )

    WindowCompat.setDecorFitsSystemWindows(this, false)

    ViewCompat.setOnApplyWindowInsetsListener(decorView) { view, insets ->
        val bottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom

        WindowInsetsCompat
            .Builder()
            .setInsets(
                WindowInsetsCompat.Type.systemBars(),
                Insets.of(0, 0, 0, bottom)
            )
            .build()
            .apply { ViewCompat.onApplyWindowInsets(view, this) }
    }
}