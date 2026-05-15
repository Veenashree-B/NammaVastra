package com.example.namma_vastraself_employment.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.namma_vastraself_employment.model.TrendItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendBoardScreen(navController: NavController) {
    val trends = listOf(
        TrendItem("https://images.unsplash.com/photo-1610030469983-98e550d6193c?auto=format&fit=crop&w=800", "Emerald Green Silk Ilkal"),
        TrendItem("https://images.unsplash.com/photo-1617627143750-d86bc21e42bb?auto=format&fit=crop&w=800", "Magenta Banarasi Brocade"),
        TrendItem("https://images.unsplash.com/photo-1583391733956-3750e0ff4e8b?auto=format&fit=crop&w=800", "Classic Mustard Gold Zari"),
        TrendItem("https://images.unsplash.com/photo-1611601679655-7ca3ce40498b?auto=format&fit=crop&w=800", "Peacock Blue Molakalmuru"),
        TrendItem("https://images.unsplash.com/photo-1594190870269-03fc0139bc46?auto=format&fit=crop&w=800", "Crimson Red Temple Border"),
        TrendItem("https://images.unsplash.com/photo-1590736961141-863776632789?auto=format&fit=crop&w=800", "Pastel Cotton Jamdani"),
        TrendItem("https://images.unsplash.com/photo-1567401893414-76b7b1e5a7a5?auto=format&fit=crop&w=800", "Earth Tone Khadi Weave"),
        TrendItem("https://images.unsplash.com/photo-1528459801416-a9e53bbf4e17?auto=format&fit=crop&w=800", "Royal Purple Kanchipuram")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Trend Board 2024", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Text(
                text = "Discover the latest in handloom fashion",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                color = MaterialTheme.colorScheme.secondary
            )
            
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(trends) { item ->
                    TrendCard(item)
                }
            }
        }
    }
}

@Composable
fun TrendCard(item: TrendItem) {
    Card(
        elevation = CardDefaults.cardElevation(6.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            SubcomposeAsyncImage(
                model = item.imageUrl,
                contentDescription = item.title,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop,
                loading = {
                    Box(
                        modifier = Modifier.fillMaxSize().background(Color.LightGray.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                    }
                },
                error = {
                    Box(
                        modifier = Modifier.fillMaxSize().background(Color.LightGray.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Photo Loading...", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    }
                }
            )
            Text(
                text = item.title,
                modifier = Modifier.padding(12.dp),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
    }
}
