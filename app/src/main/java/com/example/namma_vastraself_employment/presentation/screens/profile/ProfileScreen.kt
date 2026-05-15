package com.example.namma_vastraself_employment.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.namma_vastraself_employment.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val user by viewModel.user.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("MY PROFILE", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { viewModel.logout() }) {
                        Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Logout")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { padding ->
        if (user == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Welcome to Namma Vastra", style = MaterialTheme.typography.titleLarge)
                    Text("Sign in to manage your listings", color = Color.Gray)
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(onClick = { navController.navigate(Screen.Login.route) }) {
                        Text("LOGIN / SIGN UP")
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(top = 0.dp) // Start from top for header effect
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Profile Header with Background Image
                Box(modifier = Modifier.height(220.dp).fillMaxWidth()) {
                    AsyncImage(
                        model = "https://images.unsplash.com/photo-1524230572899-a752b3835840?q=80&w=1000",
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f))
                                )
                            )
                    )
                    
                    Column(
                        modifier = Modifier.align(Alignment.BottomStart).padding(16.dp)
                    ) {
                        Text(
                            text = user?.name ?: "Craftsman",
                            color = Color.White,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Traditional Weaver | ${user?.location ?: "Karnataka"}",
                            color = Color.White.copy(alpha = 0.8f),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Avatar overlapping header slightly
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .offset(y = (-50).dp)
                        .background(Color.White, CircleShape)
                        .padding(4.dp)
                ) {
                    AsyncImage(
                        model = user?.profileImageUrl ?: "https://www.w3schools.com/howto/img_avatar.png",
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                Column(
                    modifier = Modifier.offset(y = (-30).dp).padding(horizontal = 16.dp)
                ) {
                    ProfileMenuItem(
                        icon = Icons.Default.Inventory,
                        title = "My Saree Listings",
                        onClick = { navController.navigate(Screen.MySarees.route) }
                    )
                    ProfileMenuItem(
                        icon = Icons.Default.Edit,
                        title = "Edit Profile Info",
                        onClick = { /* Navigate to Edit */ }
                    )
                    ProfileMenuItem(
                        icon = Icons.Default.VerifiedUser,
                        title = "Experience: ${user?.experience ?: "10+ Years"}",
                        onClick = { }
                    )
                    ProfileMenuItem(
                        icon = Icons.Default.Share,
                        title = "Share Shop Link",
                        onClick = { }
                    )
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    OutlinedButton(
                        onClick = { viewModel.logout() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color.Red.copy(alpha = 0.5f))
                    ) {
                        Icon(Icons.Default.Logout, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("LOGOUT")
                    }
                    
                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }
    }
}

@Composable
fun ProfileMenuItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = title, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.LightGray)
        }
    }
    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
}
