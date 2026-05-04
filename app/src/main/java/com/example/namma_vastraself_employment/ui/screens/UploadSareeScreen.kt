package com.example.namma_vastraself_employment.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.namma_vastraself_employment.viewmodel.LoomViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadSareeScreen(navController: NavController, viewModel: LoomViewModel, onUploadSuccess: () -> Unit) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    val isLoading by viewModel.isLoading.collectAsState()
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("List Your Saree") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Showcase your craftsmanship to buyers",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.Start).padding(bottom = 16.dp)
            )

            Card(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    if (imageUri != null) {
                        AsyncImage(
                            model = imageUri,
                            contentDescription = "Selected Saree",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Surface(
                            modifier = Modifier.align(Alignment.BottomEnd).padding(8.dp),
                            color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.8f),
                            shape = MaterialTheme.shapes.small
                        ) {
                            Text("Change Photo", modifier = Modifier.padding(8.dp), style = MaterialTheme.typography.labelSmall)
                        }
                    } else {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.AddAPhoto,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Tap to Add Saree Photo", fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("What are you selling?") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("e.g. Pure Silk Ilkal Saree with Temple Border") },
                shape = MaterialTheme.shapes.medium
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Expected Price (₹)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                placeholder = { Text("e.g. 5000") },
                shape = MaterialTheme.shapes.medium,
                prefix = { Text("₹ ") }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("WhatsApp Number for Buyers") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                placeholder = { Text("919876543210") },
                supportingText = { Text("Format: CountryCode + Number (e.g., 91 for India)") },
                shape = MaterialTheme.shapes.medium
            )

            Spacer(modifier = Modifier.height(32.dp))
            
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = {
                        val p = price.toDoubleOrNull() ?: 0.0
                        if (imageUri != null && description.isNotBlank() && p > 0 && phoneNumber.length >= 10) {
                            viewModel.uploadSaree(imageUri!!, description, p, phoneNumber) {
                                Toast.makeText(context, "Saree listed successfully!", Toast.LENGTH_LONG).show()
                                onUploadSuccess()
                            }
                        } else {
                            Toast.makeText(context, "Please fill all details correctly", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = MaterialTheme.shapes.medium,
                    enabled = imageUri != null && description.isNotBlank() && price.isNotBlank() && phoneNumber.isNotBlank()
                ) {
                    Text("Post to Marketplace", style = MaterialTheme.typography.titleMedium)
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}