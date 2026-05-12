package com.shopnova.ui.screens.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.shopnova.ui.components.NovaTopBar
import com.shopnova.ui.theme.*
import com.shopnova.viewmodel.MainViewModel

@Composable
fun CartScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit,
    onCheckout: (Int, Int) -> Unit,
    onProductClick: (Int) -> Unit
) {
    val cartItems = viewModel.cartItems
    val total = viewModel.cartTotal

    Scaffold(
        topBar = {
            NovaTopBar(
                title = "My Cart",
                showBack = true,
                onBack = onBack,
                cartCount = viewModel.cartCount
            )
        },
        bottomBar = {
            if (cartItems.isNotEmpty()) {
                Surface(
                    shadowElevation = 8.dp,
                    color = Color.White
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .navigationBarsPadding(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Total Amount", color = NovaGrey, fontSize = 12.sp)
                            Text("₹${String.format("%,.0f", total)}", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp, color = NovaDeepTeal)
                        }
                        Button(
                            onClick = { 
                                // For now, we checkout the first item or handle multi-item in future
                                // Simplifying: Navigate to billing for the last added item or a summary
                                if (cartItems.isNotEmpty()) {
                                    onCheckout(cartItems.first().product.id, cartItems.first().quantity)
                                }
                            },
                            modifier = Modifier.height(50.dp).width(160.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = NovaDeepTeal)
                        ) {
                            Text("Checkout", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    ) { padding ->
        if (cartItems.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize().padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("🛒", fontSize = 64.sp)
                Spacer(Modifier.height(16.dp))
                Text("Your cart is empty", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("Add some items to get started", color = NovaGrey, fontSize = 14.sp)
                Spacer(Modifier.height(24.dp))
                OutlinedButton(onClick = onBack) {
                    Text("Go Shopping")
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().background(NovaSurface).padding(padding),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(cartItems) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        onClick = { onProductClick(item.product.id) }
                    ) {
                        Row(modifier = Modifier.padding(12.dp)) {
                            AsyncImage(
                                model = item.product.imageUrl,
                                contentDescription = item.product.name,
                                modifier = Modifier.size(80.dp).clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                                    Text(item.product.name, fontWeight = FontWeight.Bold, fontSize = 14.sp, maxLines = 1)
                                    IconButton(onClick = { viewModel.removeFromCart(item.product.id) }, modifier = Modifier.size(20.dp)) {
                                        Icon(Icons.Filled.Delete, null, tint = NovaRed, modifier = Modifier.size(18.dp))
                                    }
                                }
                                Text(item.product.brand, color = NovaGrey, fontSize = 12.sp)
                                Spacer(Modifier.height(8.dp))
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                                    Text("₹${String.format("%,.0f", item.product.price)}", fontWeight = FontWeight.ExtraBold, color = NovaDeepTeal, fontSize = 16.sp)
                                    
                                    // Quantity Selector
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        IconButton(
                                            onClick = { if (item.quantity > 1) viewModel.addToCart(item.product, -1) },
                                            modifier = Modifier.size(24.dp).background(NovaLightGrey, CircleShape)
                                        ) {
                                            Icon(Icons.Filled.Remove, null, modifier = Modifier.size(14.dp))
                                        }
                                        Text("${item.quantity}", modifier = Modifier.padding(horizontal = 10.dp), fontWeight = FontWeight.Bold)
                                        IconButton(
                                            onClick = { viewModel.addToCart(item.product, 1) },
                                            modifier = Modifier.size(24.dp).background(NovaDeepTeal, CircleShape)
                                        ) {
                                            Icon(Icons.Filled.Add, null, tint = Color.White, modifier = Modifier.size(14.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
