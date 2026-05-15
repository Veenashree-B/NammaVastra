package com.example.namma_vastraself_employment.presentation.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.namma_vastraself_employment.presentation.navigation.BottomNavItem
import com.example.namma_vastraself_employment.presentation.navigation.Screen
import com.example.namma_vastraself_employment.presentation.screens.gallery.LoomGalleryScreen
import com.example.namma_vastraself_employment.presentation.screens.pricing.PricingCalculatorScreen
import com.example.namma_vastraself_employment.presentation.screens.story.WeaverStoryScreen
import com.example.namma_vastraself_employment.presentation.screens.trends.TrendBoardScreen
import com.example.namma_vastraself_employment.presentation.screens.upload.UploadSareeScreen

@Composable
fun HomeScreen(navController: NavController) {
    val bottomNavController = rememberNavController()
    
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = bottomNavController)
        }
    ) { paddingValues ->
        NavHost(
            navController = bottomNavController,
            startDestination = Screen.TrendBoard.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.TrendBoard.route) {
                TrendBoardScreen(navController)
            }
            composable(Screen.LoomGallery.route) {
                LoomGalleryScreen(navController)
            }
            composable(Screen.UploadSaree.route) {
                UploadSareeScreen(navController)
            }
            composable(Screen.PricingCalculator.route) {
                PricingCalculatorScreen(navController)
            }
            composable(Screen.WeaverStory.route) {
                WeaverStoryScreen(navController)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Trends,
        BottomNavItem.Gallery,
        BottomNavItem.Upload,
        BottomNavItem.Pricing,
        BottomNavItem.Story
    )
    
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
