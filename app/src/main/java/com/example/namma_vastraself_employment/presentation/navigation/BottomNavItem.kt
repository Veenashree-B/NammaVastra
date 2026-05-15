package com.example.namma_vastraself_employment.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val title: String,
    val route: String,
    val icon: ImageVector
) {
    object Trends : BottomNavItem("Trends", Screen.TrendBoard.route, Icons.Default.TrendingUp)
    object Gallery : BottomNavItem("Gallery", Screen.LoomGallery.route, Icons.Default.Storefront)
    object Upload : BottomNavItem("Upload", Screen.UploadSaree.route, Icons.Default.AddPhotoAlternate)
    object Pricing : BottomNavItem("Pricing", Screen.PricingCalculator.route, Icons.Default.Calculate)
    object Story : BottomNavItem("Story", Screen.WeaverStory.route, Icons.Default.HistoryEdu)
}
