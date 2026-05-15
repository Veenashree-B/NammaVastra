package com.example.namma_vastraself_employment.presentation.screens.trends

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.namma_vastraself_employment.domain.model.TrendItem
import com.example.namma_vastraself_employment.presentation.components.ShimmerPlaceholder
import com.example.namma_vastraself_employment.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendBoardScreen(
    navController: NavController,
    viewModel: TrendViewModel = hiltViewModel()
) {
    val trends by viewModel.trends.collectAsState()
    val savedTrends by viewModel.savedTrends.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("TREND BOARD 2024", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold) }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            if (isLoading && trends.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    contentPadding = PaddingValues(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalItemSpacing = 12.dp,
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(trends) { trend ->
                        TrendCard(
                            trend = trend,
                            isSaved = savedTrends.any { it.id == trend.id },
                            onSaveToggle = {
                                if (savedTrends.any { it.id == trend.id }) {
                                    viewModel.deleteTrend(trend.id)
                                } else {
                                    viewModel.saveTrend(trend)
                                }
                            },
                            onClick = {
                                navController.navigate(Screen.TrendDetail.createRoute(trend.id))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TrendCard(
    trend: TrendItem,
    isSaved: Boolean,
    onSaveToggle: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Box {
            SubcomposeAsyncImage(
                model = trend.imageUrl,
                contentDescription = trend.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp), // Fixed height to prevent "empty" look
                contentScale = ContentScale.Crop,
                loading = { ShimmerPlaceholder() },
                error = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.ImageNotSupported, contentDescription = null, tint = Color.Gray)
                            Text("Loading...", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                        }
                    }
                }
            )
            
            IconButton(
                onClick = onSaveToggle,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
                    .background(Color.Black.copy(alpha = 0.2f), MaterialTheme.shapes.extraSmall)
            ) {
                Icon(
                    imageVector = if (isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    contentDescription = "Save",
                    tint = if (isSaved) MaterialTheme.colorScheme.primary else Color.White
                )
            }
        }
        
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = trend.title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                maxLines = 2
            )
            Text(
                text = trend.category,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary
            )
            
            Row(
                modifier = Modifier.padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                trend.colorPalette.forEach { colorStr ->
                    val color = remember(colorStr) {
                        try {
                            Color(android.graphics.Color.parseColor(colorStr))
                        } catch (e: Exception) {
                            Color.Transparent
                        }
                    }
                    if (color != Color.Transparent) {
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .background(color, CircleShape)
                        )
                    }
                }
            }
        }
    }
}
