package com.example.namma_vastraself_employment.presentation.screens.pricing

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PricingCalculatorScreen(navController: NavController) {
    var materialCost by remember { mutableStateOf("") }
    var weavingHours by remember { mutableStateOf("") }
    var materialType by remember { mutableStateOf("Silk") }
    
    val materialTypes = listOf("Silk", "Cotton", "Art Silk", "Other")
    var expanded by remember { mutableStateOf(false) }

    // Formula: FRP = (MaterialCost + (WeavingHours × 50)) × 1.15 × 1.5
    val frp = remember(materialCost, weavingHours) {
        val mCost = materialCost.toDoubleOrNull() ?: 0.0
        val wHours = weavingHours.toDoubleOrNull() ?: 0.0
        if (mCost > 0 && wHours > 0) {
            (mCost + (wHours * 50.0)) * 1.15 * 1.5
        } else 0.0
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("PRICING CALCULATOR") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = "https://images.unsplash.com/photo-1625910513397-2495914757ba?q=80&w=1000",
                contentDescription = "Weaving Materials",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Calculate a fair market price for your handcrafted saree.",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = materialType,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Material Type") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable).fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        materialTypes.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type) },
                                onClick = {
                                    materialType = type
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = materialCost,
                    onValueChange = { materialCost = it },
                    label = { Text("Material Cost (₹)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    prefix = { Text("₹ ") }
                )

                OutlinedTextField(
                    value = weavingHours,
                    onValueChange = { weavingHours = it },
                    label = { Text("Weaving Hours") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    suffix = { Text(" Hrs") }
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                if (frp > 0) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Fair Retail Price (FRP)",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = "₹${"%,.2f".format(frp)}",
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    
                    Text(
                        text = "Cost Breakdown:",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.align(Alignment.Start),
                        fontWeight = FontWeight.Bold
                    )
                    
                    val laborCost = (weavingHours.toDoubleOrNull() ?: 0.0) * 50.0
                    val totalProduction = (materialCost.toDoubleOrNull() ?: 0.0) + laborCost
                    
                    BreakdownRow("Raw Materials", "₹${materialCost}")
                    BreakdownRow("Labor Charges", "₹${"%.2f".format(laborCost)}")
                    BreakdownRow("Production Total", "₹${"%.2f".format(totalProduction)}")
                    BreakdownRow("Weaver's Margin (15%)", "₹${"%.2f".format(totalProduction * 0.15)}")
                    BreakdownRow("Market Retail (50%)", "₹${"%.2f".format(totalProduction * 1.15 * 0.5)}")
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .padding(top = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Enter details above to see pricing breakdown",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.secondary,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun BreakdownRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
    }
}
