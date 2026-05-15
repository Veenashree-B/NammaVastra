package com.example.namma_vastraself_employment.presentation.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Home : Screen("home")
    object TrendBoard : Screen("trends")
    object TrendDetail : Screen("trend_detail/{trendId}") {
        fun createRoute(trendId: String) = "trend_detail/$trendId"
    }
    object LoomGallery : Screen("gallery")
    object SareeDetail : Screen("saree_detail/{sareeId}") {
        fun createRoute(sareeId: String) = "saree_detail/$sareeId"
    }
    object UploadSaree : Screen("upload")
    object PricingCalculator : Screen("pricing")
    object WeaverStory : Screen("story")
    object Profile : Screen("profile")
    object MySarees : Screen("my_sarees")
}
