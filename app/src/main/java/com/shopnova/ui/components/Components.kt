package com.shopnova.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.shopnova.data.model.Product
import com.shopnova.ui.theme.*

@Composable
fun ProductCard(
    product: Product,
    onClick: () -> Unit,
    onWishlist: () -> Unit,
    isWishlisted: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(180.dp)
            .clickable(onClick = onClick)
            .shadow(2.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Box {
            // Product Image
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                contentScale = ContentScale.Crop
            )
            // Badge
            product.badge?.let {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(
                            when (it) {
                                "Best Seller" -> NovaAmber
                                "New" -> NovaDeepTeal
                                "Sale" -> NovaRed
                                else -> NovaDeepTeal
                            }
                        )
                        .padding(horizontal = 6.dp, vertical = 3.dp)
                ) {
                    Text(it, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
            // Wishlist
            IconButton(
                onClick = onWishlist,
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    if (isWishlisted) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Wishlist",
                    tint = if (isWishlisted) NovaRed else Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                product.brand,
                fontSize = 11.sp,
                color = NovaGrey,
                fontWeight = FontWeight.Medium
            )
            Text(
                product.name,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 17.sp
            )
            Spacer(Modifier.height(4.dp))
            // Stars
            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(5) { i ->
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = null,
                        tint = if (i < product.rating.toInt()) NovaAmber else NovaLightGrey,
                        modifier = Modifier.size(12.dp)
                    )
                }
                Text(
                    " (${product.reviewCount})",
                    fontSize = 10.sp,
                    color = NovaGrey
                )
            }
            Spacer(Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "₹${String.format("%,.0f", product.price)}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = NovaDeepTeal
                )
                if (product.discountPercent > 0) {
                    Spacer(Modifier.width(4.dp))
                    Text(
                        "${product.discountPercent}% off",
                        fontSize = 11.sp,
                        color = NovaGreen,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            if (product.originalPrice > product.price) {
                Text(
                    "₹${String.format("%,.0f", product.originalPrice)}",
                    fontSize = 11.sp,
                    color = NovaGrey,
                    textDecoration = TextDecoration.LineThrough
                )
            }
        }
    }
}

@Composable
fun NovaTopBar(
    title: String = "ShopNova",
    showBack: Boolean = false,
    onBack: () -> Unit = {},
    cartCount: Int = 0,
    onCartClick: () -> Unit = {},
    actions: @Composable () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.horizontalGradient(listOf(NovaDeepTeal, NovaTealLight))
            )
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (showBack) {
                IconButton(onClick = onBack, modifier = Modifier.size(36.dp)) {
                    Icon(Icons.Filled.ArrowBack, "Back", tint = Color.White)
                }
                Spacer(Modifier.width(4.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                if (!showBack) {
                    Text("ShopNova", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
                } else {
                    Text(title, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
            actions()
            // Cart icon with badge
            Box {
                IconButton(onClick = onCartClick) {
                    Icon(Icons.Filled.ShoppingCart, "Cart", tint = Color.White)
                }
                if (cartCount > 0) {
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
                            if (cartCount > 9) "9+" else cartCount.toString(),
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StarRatingBar(rating: Float, reviewCount: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        repeat(5) { i ->
            Icon(
                Icons.Filled.Star,
                contentDescription = null,
                tint = if (i < rating.toInt()) NovaAmber else NovaLightGrey,
                modifier = Modifier.size(16.dp)
            )
        }
        Spacer(Modifier.width(6.dp))
        Text("$rating", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
        Text(" · $reviewCount reviews", color = NovaGrey, fontSize = 13.sp)
    }
}

@Composable
fun SectionHeader(title: String, onSeeAll: (() -> Unit)? = null) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = NovaOnSurface)
        if (onSeeAll != null) {
            TextButton(onClick = onSeeAll) {
                Text("See All", color = NovaDeepTeal, fontSize = 13.sp)
            }
        }
    }
}

@Composable
fun NovaButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    secondary: Boolean = false,
    icon: @Composable (() -> Unit)? = null
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.height(52.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (secondary) NovaAmber else NovaDeepTeal,
            contentColor = Color.White,
            disabledContainerColor = Color(0xFFCCCCCC)
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        icon?.invoke()
        if (icon != null) Spacer(Modifier.width(8.dp))
        Text(text, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}
