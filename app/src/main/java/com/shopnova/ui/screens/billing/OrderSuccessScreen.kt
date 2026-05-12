package com.shopnova.ui.screens.billing

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shopnova.ui.theme.*

@Composable
fun OrderSuccessScreen(onContinueShopping: () -> Unit) {
    val orderId = remember { "SN${(100000..999999).random()}" }

    val scale = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        scale.animateTo(1f, animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(NovaDeepTeal, NovaTealLight, Color(0xFFE0F2F1)))),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Animated checkmark
        Box(
            modifier = Modifier
                .scale(scale.value)
                .size(140.dp)
                .clip(CircleShape)
                .background(Color.White.copy(0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.CheckCircle,
                    contentDescription = null,
                    tint = NovaGreen,
                    modifier = Modifier.size(80.dp)
                )
            }
        }

        Spacer(Modifier.height(32.dp))

        Text("Order Placed! 🎉", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
        Spacer(Modifier.height(8.dp))
        Text(
            "Your order has been confirmed\nand is on its way!",
            color = Color.White.copy(0.85f), fontSize = 16.sp,
            textAlign = TextAlign.Center, lineHeight = 24.sp
        )

        Spacer(Modifier.height(24.dp))

        // Order ID card
        Card(
            modifier = Modifier.padding(horizontal = 32.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(0.15f))
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Order ID", color = Color.White.copy(0.7f), fontSize = 12.sp)
                Text(orderId, color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
                Spacer(Modifier.height(12.dp))

                // Delivery steps
                listOf(
                    Triple(Icons.Filled.Inventory2, "Order Confirmed", true),
                    Triple(Icons.Filled.LocalShipping, "Dispatched", false),
                    Triple(Icons.Filled.Home, "Delivered", false),
                ).forEach { (icon, label, done) ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(if (done) NovaGreen else Color.White.copy(0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(icon, null, tint = Color.White, modifier = Modifier.size(16.dp))
                        }
                        Spacer(Modifier.width(10.dp))
                        Text(label, color = Color.White, fontSize = 14.sp, fontWeight = if (done) FontWeight.Bold else FontWeight.Normal)
                        Spacer(Modifier.weight(1f))
                        if (done) Icon(Icons.Filled.CheckCircle, null, tint = NovaGreen, modifier = Modifier.size(18.dp))
                    }
                }
            }
        }

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = onContinueShopping,
            modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Icon(Icons.Filled.ShoppingBag, null, tint = NovaDeepTeal, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(8.dp))
            Text("Continue Shopping", color = NovaDeepTeal, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
        }

        Spacer(Modifier.height(12.dp))

        var showTrackMsg by remember { mutableStateOf(false) }
        
        if (showTrackMsg) {
            Text(
                "Tracking logic will be implemented here!",
                color = NovaDeepTeal,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        TextButton(onClick = { showTrackMsg = true }) {
            Text("Track Order", color = Color.White.copy(0.8f), fontSize = 14.sp)
        }
    }
}
