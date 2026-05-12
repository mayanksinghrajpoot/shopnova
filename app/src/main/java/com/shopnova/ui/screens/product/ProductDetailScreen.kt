package com.shopnova.ui.screens.product

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.shopnova.data.repository.ProductRepository
import com.shopnova.ui.components.*
import com.shopnova.ui.theme.*
import com.shopnova.viewmodel.MainViewModel

@Composable
fun ProductDetailScreen(
    productId: Int,
    viewModel: MainViewModel,
    onBack: () -> Unit,
    onBuyNow: (Int) -> Unit,
    onLoginRequired: () -> Unit,
    onCartClick: () -> Unit
) {
    val product = remember(productId) { ProductRepository.getProductById(productId) }
    val cartCount = viewModel.cartCount
    val isWishlisted = viewModel.isWishlisted(productId)
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

    if (product == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Product not found")
        }
        return
    }

    Scaffold(
        topBar = {
            NovaTopBar(
                title = product.name,
                showBack = true,
                onBack = onBack,
                cartCount = cartCount,
                onCartClick = onCartClick
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White,
                contentPadding = PaddingValues(16.dp),
                tonalElevation = 8.dp
            ) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    NovaButton(
                        text = "Add to Cart",
                        onClick = { viewModel.addToCart(product, 1) },
                        modifier = Modifier.weight(1f),
                        secondary = true,
                        icon = { Icon(Icons.Filled.AddShoppingCart, null) }
                    )
                    Spacer(Modifier.width(16.dp))
                    NovaButton(
                        text = "Buy Now",
                        onClick = { 
                            if (isLoggedIn) onBuyNow(1) else onLoginRequired() 
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Product Image
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        product.brand,
                        color = NovaGrey,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(Modifier.weight(1f))
                    IconButton(onClick = { viewModel.toggleWishlist(product.id) }) {
                        Icon(
                            if (isWishlisted) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Wishlist",
                            tint = if (isWishlisted) NovaRed else NovaGrey
                        )
                    }
                }

                Text(
                    product.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = NovaOnSurface
                )

                Spacer(Modifier.height(8.dp))

                StarRatingBar(rating = product.rating, reviewCount = product.reviewCount)

                Spacer(Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        "₹${String.format("%,.0f", product.price)}",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = NovaDeepTeal
                    )
                    if (product.originalPrice > product.price) {
                        Spacer(Modifier.width(12.dp))
                        Text(
                            "₹${String.format("%,.0f", product.originalPrice)}",
                            fontSize = 16.sp,
                            color = NovaGrey,
                            textDecoration = TextDecoration.LineThrough
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "${product.discountPercent}% OFF",
                            fontSize = 16.sp,
                            color = NovaGreen,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))

                Text("Description", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text(
                    product.description,
                    fontSize = 15.sp,
                    color = Color.Gray,
                    lineHeight = 22.sp
                )

                Spacer(Modifier.height(24.dp))

                Text("Features", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                product.features.forEach { feature ->
                    Row(
                        modifier = Modifier.padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Filled.CheckCircle,
                            contentDescription = null,
                            tint = NovaGreen,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(feature, fontSize = 14.sp)
                    }
                }

                Spacer(Modifier.height(24.dp))
                
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Filled.LocalShipping, null, tint = NovaDeepTeal)
                        Spacer(Modifier.width(12.dp))
                        Text(
                            "Delivery in ${product.deliveryDays} days",
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}
