package com.shopnova.ui.screens.dashboard

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.shopnova.data.model.Category
import com.shopnova.data.model.Product
import com.shopnova.data.repository.ProductRepository
import com.shopnova.ui.components.*
import com.shopnova.ui.theme.*
import com.shopnova.viewmodel.MainViewModel

@Composable
fun DashboardScreen(
    viewModel: MainViewModel,
    onProductClick: (Int) -> Unit,
    onLoginClick: () -> Unit
) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    val focusManager = LocalFocusManager.current

    val displayedProducts = remember(searchQuery, selectedCategory) {
        val base = if (selectedCategory != null)
            ProductRepository.getProductsByCategory(selectedCategory!!)
        else
            ProductRepository.allProducts
        if (searchQuery.isBlank()) base
        else ProductRepository.searchProducts(searchQuery).let { results ->
            if (selectedCategory != null) results.filter { it.category == selectedCategory } else results
        }
    }

    val isSearching = searchQuery.isNotBlank()

    Column(modifier = Modifier.fillMaxSize().background(NovaSurface)) {
        // Top Bar
        Column(
            modifier = Modifier
                .background(Brush.horizontalGradient(listOf(NovaDeepTeal, NovaTealLight)))
                .statusBarsPadding()
                .padding(bottom = 12.dp)
        ) {
            // Header row
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("ShopNova", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
                    if (isLoggedIn) {
                        val user by viewModel.currentUser.collectAsState()
                        Text("Hey ${user?.name?.split(" ")?.first() ?: ""}! 👋", color = Color.White.copy(0.85f), fontSize = 12.sp)
                    } else {
                        Text("Delivering to Phagwara", color = Color.White.copy(0.85f), fontSize = 12.sp)
                    }
                }
                Row {
                    // Profile / Login
                    IconButton(onClick = if (isLoggedIn) ({}) else onLoginClick) {
                        Icon(
                            if (isLoggedIn) Icons.Filled.AccountCircle else Icons.Filled.Person,
                            contentDescription = "Account",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    // Cart
                    Box {
                        IconButton(onClick = {}) {
                            Icon(Icons.Filled.ShoppingCart, "Cart", tint = Color.White, modifier = Modifier.size(26.dp))
                        }
                        if (viewModel.cartCount > 0) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .offset(x = (-4).dp, y = 4.dp)
                                    .size(18.dp)
                                    .clip(CircleShape)
                                    .background(NovaBadge),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    if (viewModel.cartCount > 9) "9+" else viewModel.cartCount.toString(),
                                    color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color.White),
                placeholder = {
                    Text("Search phones, fashion, groceries...", color = NovaGrey, fontSize = 14.sp)
                },
                leadingIcon = {
                    Icon(Icons.Filled.Search, "Search", tint = NovaDeepTeal)
                },
                trailingIcon = {
                    if (searchQuery.isNotBlank()) {
                        IconButton(onClick = {
                            searchQuery = ""
                            focusManager.clearFocus()
                        }) {
                            Icon(Icons.Filled.Close, "Clear", tint = NovaGrey)
                        }
                    } else {
                        Icon(Icons.Filled.Mic, "Voice", tint = NovaGrey)
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                ),
                shape = RoundedCornerShape(14.dp)
            )
        }

        // Content
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            // If searching, show search results
            if (isSearching) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Results for \"$searchQuery\"",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text("${displayedProducts.size} items", color = NovaGrey, fontSize = 13.sp)
                    }
                }
                if (displayedProducts.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(40.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("😕", fontSize = 48.sp)
                            Spacer(Modifier.height(12.dp))
                            Text("No products found", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                            Text("Try searching something else", color = NovaGrey, fontSize = 14.sp)
                        }
                    }
                } else {
                    items(displayedProducts.chunked(2)) { rowProducts ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 6.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            rowProducts.forEach { product ->
                                ProductCard(
                                    product = product,
                                    onClick = { onProductClick(product.id) },
                                    onWishlist = { viewModel.toggleWishlist(product.id) },
                                    isWishlisted = viewModel.isWishlisted(product.id),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            if (rowProducts.size == 1) Spacer(Modifier.weight(1f))
                        }
                    }
                }
                return@LazyColumn
            }

            // BANNER
            item {
                PromoBanner()
            }

            // CATEGORIES
            item {
                SectionHeader("Shop by Category")
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        CategoryChip(
                            name = "All",
                            icon = "🛍️",
                            color = NovaDeepTeal,
                            selected = selectedCategory == null,
                            onClick = { selectedCategory = null }
                        )
                    }
                    items(ProductRepository.categories) { cat ->
                        CategoryChip(
                            name = cat.name,
                            icon = cat.icon,
                            color = Color(cat.color),
                            selected = selectedCategory == cat.name,
                            onClick = {
                                selectedCategory = if (selectedCategory == cat.name) null else cat.name
                            }
                        )
                    }
                }
                Spacer(Modifier.height(8.dp))
            }

            // DEALS OF THE DAY
            item {
                DealsBanner()
            }

            // BEST SELLERS
            item {
                SectionHeader("🔥 Best Sellers")
            }
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(ProductRepository.getBestSellers()) { product ->
                        ProductCard(
                            product = product,
                            onClick = { onProductClick(product.id) },
                            onWishlist = { viewModel.toggleWishlist(product.id) },
                            isWishlisted = viewModel.isWishlisted(product.id)
                        )
                    }
                }
                Spacer(Modifier.height(8.dp))
            }

            // FILTERED OR ALL PRODUCTS GRID
            val filtered = if (selectedCategory != null)
                ProductRepository.getProductsByCategory(selectedCategory!!)
            else
                ProductRepository.allProducts

            item {
                SectionHeader(
                    if (selectedCategory != null) "$selectedCategory Products" else "All Products"
                )
            }

            items(filtered.chunked(2)) { rowProducts ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowProducts.forEach { product ->
                        ProductCard(
                            product = product,
                            onClick = { onProductClick(product.id) },
                            onWishlist = { viewModel.toggleWishlist(product.id) },
                            isWishlisted = viewModel.isWishlisted(product.id),
                            modifier = Modifier.weight(1f)
                        )
                    }
                    if (rowProducts.size == 1) Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun PromoBanner() {
    val banners = listOf(
        Triple("Summer Sale 🌞", "Up to 70% off on Fashion", Brush.horizontalGradient(listOf(Color(0xFF0D6E6E), Color(0xFF1A9696)))),
        Triple("Electronics Fest ⚡", "Deals starting ₹499", Brush.horizontalGradient(listOf(Color(0xFF1565C0), Color(0xFF42A5F5)))),
        Triple("Fresh Arrivals 🆕", "New products every day", Brush.horizontalGradient(listOf(Color(0xFF6A1B9A), Color(0xFFAB47BC)))),
    )
    var currentBanner by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(3000)
            currentBanner = (currentBanner + 1) % banners.size
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(banners[currentBanner].third)
            .height(160.dp)
            .padding(20.dp)
    ) {
        Column(modifier = Modifier.align(Alignment.CenterStart)) {
            Text(banners[currentBanner].first, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(Modifier.height(4.dp))
            Text(banners[currentBanner].second, color = Color.White.copy(0.9f), fontSize = 14.sp)
            Spacer(Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White.copy(0.2f))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text("Shop Now →", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        }

        // Dots
        Row(
            modifier = Modifier.align(Alignment.BottomEnd),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            repeat(banners.size) { i ->
                Box(
                    modifier = Modifier
                        .size(if (i == currentBanner) 20.dp else 8.dp, 8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(if (i == currentBanner) Color.White else Color.White.copy(0.4f))
                )
            }
        }
    }
}

@Composable
fun DealsBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Brush.horizontalGradient(listOf(Color(0xFFFF8F00), Color(0xFFFFB300))))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("⚡ Deal of the Day", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                Text("Ends in 4h 23m", color = Color.White.copy(0.9f), fontSize = 12.sp)
            }
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(ProductRepository.getDealsOfDay().take(3)) { p ->
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.White.copy(0.2f))
                    ) {
                        AsyncImage(
                            model = p.imageUrl,
                            contentDescription = p.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryChip(name: String, icon: String, color: Color, selected: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(if (selected) color else color.copy(0.12f))
                .border(
                    width = if (selected) 0.dp else 1.dp,
                    color = color.copy(0.3f),
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(icon, fontSize = 24.sp)
        }
        Spacer(Modifier.height(4.dp))
        Text(
            name,
            fontSize = 11.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            color = if (selected) color else NovaOnSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
