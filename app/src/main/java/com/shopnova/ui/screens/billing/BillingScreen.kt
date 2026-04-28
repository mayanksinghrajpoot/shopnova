package com.shopnova.ui.screens.billing

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.shopnova.data.repository.ProductRepository
import com.shopnova.ui.screens.login.NovaTextField
import com.shopnova.ui.theme.*
import com.shopnova.viewmodel.MainViewModel

@Composable
fun BillingScreen(
    productId: Int,
    quantity: Int,
    viewModel: MainViewModel,
    onBack: () -> Unit,
    onOrderPlaced: () -> Unit
) {
    val product = ProductRepository.getProductById(productId) ?: run { onBack(); return }
    val user by viewModel.currentUser.collectAsState()

    var fullName by remember { mutableStateOf(user?.name ?: "") }
    var phone by remember { mutableStateOf(user?.phone ?: "") }
    var addressLine by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var pincode by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var selectedPayment by remember { mutableStateOf("UPI") }

    val deliveryCharge = if (product.price * quantity >= 499) 0.0 else 49.0
    val subtotal = product.price * quantity
    val total = subtotal + deliveryCharge
    val savings = (product.originalPrice - product.price) * quantity

    val formValid = fullName.isNotBlank() && phone.length >= 10 &&
            addressLine.isNotBlank() && city.isNotBlank() &&
            pincode.length == 6 && state.isNotBlank()

    Column(modifier = Modifier.fillMaxSize().background(NovaSurface)) {
        // Top bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.horizontalGradient(listOf(NovaDeepTeal, NovaTealLight)))
                .statusBarsPadding()
                .padding(horizontal = 8.dp, vertical = 12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.ArrowBack, "Back", tint = Color.White)
                }
                Text("Checkout", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }

        // Progress indicator
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("Address", "Payment", "Review").forEachIndexed { idx, step ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(androidx.compose.foundation.shape.CircleShape)
                            .background(if (idx <= 1) NovaDeepTeal else NovaDivider),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            if (idx <= 0) "✓" else "${idx + 1}",
                            color = if (idx <= 1) Color.White else NovaGrey,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    if (idx < 2) {
                        Box(
                            modifier = Modifier
                                .width(50.dp)
                                .height(2.dp)
                                .background(if (idx < 1) NovaDeepTeal else NovaDivider)
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            // Product summary
            Card(
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = product.imageUrl,
                        contentDescription = product.name,
                        modifier = Modifier.size(70.dp).clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(product.name, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, maxLines = 2)
                        Text(product.brand, color = NovaGrey, fontSize = 12.sp)
                        Spacer(Modifier.height(4.dp))
                        Row {
                            Text("₹${String.format("%,.0f", product.price)}", fontWeight = FontWeight.Bold, color = NovaDeepTeal, fontSize = 15.sp)
                            Spacer(Modifier.width(6.dp))
                            Text("× $quantity", color = NovaGrey, fontSize = 14.sp)
                        }
                    }
                }
            }

            // Delivery Address
            SectionCard(title = "📍 Delivery Address") {
                NovaTextField(value = fullName, onValueChange = { fullName = it }, label = "Full Name", icon = Icons.Filled.Person)
                Spacer(Modifier.height(10.dp))
                NovaTextField(value = phone, onValueChange = { phone = it }, label = "Phone Number", icon = Icons.Filled.Phone, keyboardType = KeyboardType.Phone)
                Spacer(Modifier.height(10.dp))
                NovaTextField(value = addressLine, onValueChange = { addressLine = it }, label = "House No., Street, Area", icon = Icons.Filled.Home)
                Spacer(Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Column(modifier = Modifier.weight(1f)) {
                        NovaTextField(value = city, onValueChange = { city = it }, label = "City", icon = Icons.Filled.LocationCity)
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        NovaTextField(value = pincode, onValueChange = { if (it.length <= 6) pincode = it }, label = "Pincode", icon = Icons.Filled.Pin, keyboardType = KeyboardType.Number)
                    }
                }
                Spacer(Modifier.height(10.dp))
                NovaTextField(value = state, onValueChange = { state = it }, label = "State", icon = Icons.Filled.Map)
            }

            // Payment Method
            SectionCard(title = "💳 Payment Method") {
                val methods = listOf(
                    Triple("UPI", "Google Pay, PhonePe, Paytm", Icons.Filled.AccountBalanceWallet),
                    Triple("Credit / Debit Card", "Visa, Mastercard, Rupay", Icons.Filled.CreditCard),
                    Triple("Net Banking", "All major banks", Icons.Filled.AccountBalance),
                    Triple("Cash on Delivery", "Pay when delivered", Icons.Filled.Money),
                )
                methods.forEach { (method, subtitle, icon) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .border(
                                width = if (selectedPayment == method) 2.dp else 1.dp,
                                color = if (selectedPayment == method) NovaDeepTeal else NovaDivider,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .background(if (selectedPayment == method) Color(0xFFE0F2F1) else Color.White)
                            .clickable { selectedPayment = method }
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(icon, null, tint = if (selectedPayment == method) NovaDeepTeal else NovaGrey, modifier = Modifier.size(22.dp))
                        Spacer(Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(method, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                            Text(subtitle, color = NovaGrey, fontSize = 12.sp)
                        }
                        RadioButton(
                            selected = selectedPayment == method,
                            onClick = { selectedPayment = method },
                            colors = RadioButtonDefaults.colors(selectedColor = NovaDeepTeal)
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                }
            }

            // Price Breakdown
            SectionCard(title = "🧾 Price Details") {
                PriceRow("Subtotal (${quantity} item${if (quantity > 1) "s" else ""})", "₹${String.format("%,.0f", subtotal)}")
                if (savings > 0) PriceRow("Discount", "-₹${String.format("%,.0f", savings)}", valueColor = NovaGreen)
                PriceRow("Delivery", if (deliveryCharge == 0.0) "FREE" else "₹${deliveryCharge.toInt()}", valueColor = if (deliveryCharge == 0.0) NovaGreen else NovaOnSurface)
                Divider(color = NovaDivider, modifier = Modifier.padding(vertical = 8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Total Amount", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                    Text("₹${String.format("%,.0f", total)}", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = NovaDeepTeal)
                }
                if (savings > 0) {
                    Spacer(Modifier.height(6.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFE8F5E9))
                            .padding(10.dp)
                    ) {
                        Text(
                            "🎉 You save ₹${String.format("%,.0f", savings)} on this order!",
                            color = NovaGreen, fontWeight = FontWeight.SemiBold, fontSize = 13.sp
                        )
                    }
                }
            }

            Spacer(Modifier.height(100.dp))
        }

        // Place Order button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .navigationBarsPadding()
                .padding(16.dp)
        ) {
            Column {
                if (!formValid) {
                    Text(
                        "Please fill all delivery details to proceed",
                        color = NovaGrey, fontSize = 12.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                Button(
                    onClick = {
                        if (formValid) {
                            viewModel.addToCart(product, quantity)
                            onOrderPlaced()
                        }
                    },
                    enabled = formValid,
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NovaDeepTeal,
                        disabledContainerColor = Color(0xFFCCCCCC)
                    )
                ) {
                    Icon(Icons.Filled.Lock, null, tint = Color.White, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "Place Order · ₹${String.format("%,.0f", total)}",
                        fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun SectionCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
fun PriceRow(label: String, value: String, valueColor: Color = NovaOnSurface) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = NovaGrey, fontSize = 14.sp)
        Text(value, fontWeight = FontWeight.Medium, fontSize = 14.sp, color = valueColor)
    }
}
