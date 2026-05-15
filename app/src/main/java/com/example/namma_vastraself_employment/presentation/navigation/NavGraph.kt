package com.example.namma_vastraself_employment.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.namma_vastraself_employment.presentation.screens.gallery.LoomGalleryScreen
import com.example.namma_vastraself_employment.presentation.screens.gallery.SareeDetailScreen
import com.example.namma_vastraself_employment.presentation.screens.home.HomeScreen
import com.example.namma_vastraself_employment.presentation.screens.login.LoginScreen
import com.example.namma_vastraself_employment.presentation.screens.onboarding.OnboardingScreen
import com.example.namma_vastraself_employment.presentation.screens.pricing.PricingCalculatorScreen
import com.example.namma_vastraself_employment.presentation.screens.profile.MySareesScreen
import com.example.namma_vastraself_employment.presentation.screens.profile.ProfileScreen
import com.example.namma_vastraself_employment.presentation.screens.splash.SplashScreen
import com.example.namma_vastraself_employment.presentation.screens.story.WeaverStoryScreen
import com.example.namma_vastraself_employment.presentation.screens.trends.TrendBoardScreen
import com.example.namma_vastraself_employment.presentation.screens.trends.TrendDetailScreen
import com.example.namma_vastraself_employment.presentation.screens.upload.UploadSareeScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Splash.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }
        composable("onboarding") {
            OnboardingScreen(navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.TrendDetail.route, arguments = listOf(navArgument("trendId") { type = NavType.StringType })) { backStackEntry ->
            val trendId = backStackEntry.arguments?.getString("trendId") ?: ""
            TrendDetailScreen(navController, trendId)
        }
        composable(Screen.SareeDetail.route, arguments = listOf(navArgument("sareeId") { type = NavType.StringType })) { backStackEntry ->
            val sareeId = backStackEntry.arguments?.getString("sareeId") ?: ""
            SareeDetailScreen(navController, sareeId)
        }
        composable(Screen.MySarees.route) {
            MySareesScreen(navController)
        }
        // Sub-routes usually handled by HomeScreen's inner NavHost, 
        // but included here if deep linking or full-screen navigation is needed
        composable(Screen.TrendBoard.route) { TrendBoardScreen(navController) }
        composable(Screen.LoomGallery.route) { LoomGalleryScreen(navController) }
        composable(Screen.UploadSaree.route) { UploadSareeScreen(navController) }
        composable(Screen.PricingCalculator.route) { PricingCalculatorScreen(navController) }
        composable(Screen.WeaverStory.route) { WeaverStoryScreen(navController) }
        composable(Screen.Profile.route) { ProfileScreen(navController) }
    }
}
