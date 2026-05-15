package com.example.namma_vastraself_employment.presentation.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.namma_vastraself_employment.presentation.navigation.Screen
import com.example.namma_vastraself_employment.presentation.screens.gallery.SareeCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySareesScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val user by viewModel.user.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MY LISTINGS", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            if (user != null) {
                FloatingActionButton(
                    onClick = { navController.navigate(Screen.UploadSaree.route) },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add New")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (user == null) {
                EmptyStateView(
                    imageModel = "https://images.unsplash.com/photo-1590736961141-863776632789?q=80&w=1000",
                    title = "Access Your Account",
                    description = "Please login to manage your listed sarees and view your sales.",
                    buttonText = "GO TO LOGIN",
                    onButtonClick = { navController.navigate(Screen.Login.route) }
                )
            } else {
                // Mocking empty state for weaver's own listings
                EmptyStateView(
                    imageModel = "https://images.unsplash.com/photo-1558273109-6047725c7c93?q=80&w=1000",
                    title = "No Listings Yet",
                    description = "You haven't added any sarees to your shop. Start selling by tapping the button below!",
                    buttonText = "START SELLING",
                    onButtonClick = { navController.navigate(Screen.UploadSaree.route) }
                )
            }
        }
    }
}

@Composable
fun EmptyStateView(
    imageModel: String,
    title: String,
    description: String,
    buttonText: String,
    onButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = imageModel,
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 24.dp),
            contentScale = ContentScale.Fit
        )
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onButtonClick,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(buttonText)
        }
    }
}
