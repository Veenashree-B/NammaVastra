package com.example.namma_vastraself_employment.presentation.screens.story

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeaverStoryScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("WEAVER STORY") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = "https://images.unsplash.com/photo-1583391733956-3750e0ff4e8b?q=80&w=1000",
                contentDescription = "Weaving Heritage",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "The Legacy of Ilkal and Molakalmuru",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Ilkal sarees take their name from the town of Ilkal in the Bagalkot district of Karnataka state, India. They are traditional dress worn by women in Karnataka, Andhra Pradesh, and Maharashtra. The unique feature of these sarees is the joining of the body with the pallu with a series of loops locally called as 'Topi Teni' technique.",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Molakalmuru Saree is the traditional silk saree that is manufactured in the Molakalmuru, Chitradurga district, Karnataka, India. In 2011, it has been granted a Geographical Indication tag. These sarees are known for their distinctive nature-inspired motifs like fruits, animals, and flowers.",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Techniques and Craftsmanship",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "The weaving process involves a high level of skill and patience. From dyeing the yarn to setting up the pit loom, every step is done with meticulous care. The use of pure silk and silver-dipped zari in Molakalmuru sarees makes them highly prized possessions.",
                    style = MaterialTheme.typography.bodyLarge
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Preserving Our Heritage",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Namma Vastra aims to protect these traditional art forms by giving weavers the direct market access they deserve. Every purchase supports a weaver family and keeps a century-old tradition alive.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
