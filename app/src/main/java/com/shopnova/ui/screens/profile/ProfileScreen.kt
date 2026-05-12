package com.shopnova.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shopnova.ui.components.NovaTopBar
import com.shopnova.ui.theme.*
import com.shopnova.viewmodel.MainViewModel

@Composable
fun ProfileScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit,
    onLogout: () -> Unit
) {
    val user by viewModel.currentUser.collectAsState()

    Scaffold(
        topBar = {
            NovaTopBar(title = "Profile", showBack = true, onBack = onBack)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(NovaSurface)
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.verticalGradient(listOf(NovaDeepTeal, NovaTealLight)))
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            user?.name?.firstOrNull()?.toString() ?: "U",
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Spacer(Modifier.height(16.dp))
                    Text(user?.name ?: "Guest User", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text(user?.email ?: "Sign in to see details", color = Color.White.copy(0.8f), fontSize = 14.sp)
                }
            }

            // Stats row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .offset(y = (-20).dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ProfileStat("Orders", "12")
                Divider(modifier = Modifier.height(30.dp).width(1.dp), color = NovaDivider)
                ProfileStat("Wishlist", "${viewModel.wishlistIds.size}")
                Divider(modifier = Modifier.height(30.dp).width(1.dp), color = NovaDivider)
                ProfileStat("Coupons", "5")
            }

            // Menu
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                ProfileMenuItem(Icons.Filled.Inventory, "My Orders", "Track, return or buy things again")
                ProfileMenuItem(Icons.Filled.LocationOn, "Shipping Addresses", "Manage your delivery locations")
                ProfileMenuItem(Icons.Filled.Payment, "Payment Methods", "Manage your saved cards and UPI IDs")
                ProfileMenuItem(Icons.Filled.Notifications, "Notifications", "Order updates, offers and more")
                ProfileMenuItem(Icons.Filled.Settings, "Settings", "Password, language, and theme")
                ProfileMenuItem(Icons.Filled.Help, "Help Center", "Support and contact details")

                Spacer(Modifier.height(24.dp))

                Button(
                    onClick = {
                        viewModel.logout()
                        onLogout()
                    },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, NovaRed),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Filled.Logout, null, tint = NovaRed)
                    Spacer(Modifier.width(8.dp))
                    Text("Logout", color = NovaRed, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(Modifier.height(40.dp))
        }
    }
}

@Composable
fun ProfileStat(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = NovaDeepTeal)
        Text(label, fontSize = 12.sp, color = NovaGrey)
    }
}

@Composable
fun ProfileMenuItem(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, subtitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable { }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(40.dp).clip(CircleShape).background(NovaDeepTeal.copy(0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = NovaDeepTeal, modifier = Modifier.size(20.dp))
        }
        Spacer(Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Text(subtitle, color = NovaGrey, fontSize = 12.sp)
        }
        Icon(Icons.Filled.ChevronRight, null, tint = NovaGrey)
    }
}
