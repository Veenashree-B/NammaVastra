package com.example.namma_vastraself_employment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.namma_vastraself_employment.ui.screens.*
import com.example.namma_vastraself_employment.ui.theme.NammaVastraSelfEmploymentTheme
import com.example.namma_vastraself_employment.viewmodel.LoomViewModel

class MainActivity : ComponentActivity() {
    private val loomViewModel: LoomViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NammaVastraSelfEmploymentTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") { HomeScreen(navController) }
                        composable("trend_board") { TrendBoardScreen(navController) }
                        composable("loom_gallery") { LoomGalleryScreen(navController, loomViewModel) }
                        composable("upload_saree") { 
                            UploadSareeScreen(navController, loomViewModel) {
                                navController.popBackStack()
                            }
                        }
                        composable("price_calculator") { PriceCalculatorScreen(navController) }
                        composable("weaver_story") { WeaverStoryScreen(navController) }
                    }
                }
            }
        }
    }
}