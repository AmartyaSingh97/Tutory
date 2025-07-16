package com.amartyasingh.tutory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amartyasingh.tutory.dataStore.OnboardingPreferences
import com.amartyasingh.tutory.ui.screens.MainScreen
import com.amartyasingh.tutory.ui.screens.OnboardingPagerScreen
import com.amartyasingh.tutory.ui.theme.TutoryTheme
import com.amartyasingh.tutory.viewmodel.SplashViewmodel
import kotlin.getValue

class MainActivity : ComponentActivity() {

    private val viewModel : SplashViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            MainApp()

            installSplashScreen().apply {
                setKeepOnScreenCondition{
                    viewModel.isSplashShow.value
                }
            }
        }
    }

    @Composable
    fun MainApp() {
        val context = LocalContext.current
        val onboardingCompleted by OnboardingPreferences.isOnboardingCompleted(context).collectAsState(initial = null)

        if (onboardingCompleted != null) {
            val navController = rememberNavController()
            val startDestination = if (onboardingCompleted == true) "main" else "onboarding"

            NavHost(navController = navController, startDestination = startDestination) {
                composable("main") { MainScreen() }
                composable("onboarding") {
                    OnboardingPagerScreen(
                        onFinished = {
                            navController.navigate("main") {
                                popUpTo("onboarding") { inclusive = true }
                            }
                        }
                    )
                }
            }
        }
    }
}
