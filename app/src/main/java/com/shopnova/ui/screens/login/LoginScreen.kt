package com.shopnova.ui.screens.login

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shopnova.ui.theme.*
import com.shopnova.viewmodel.MainViewModel

@Composable
fun LoginScreen(
    viewModel: MainViewModel,
    onLoginSuccess: () -> Unit,
    onBack: () -> Unit
) {
    var isSignUp by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        // Header gradient
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .background(Brush.verticalGradient(listOf(NovaDeepTeal, NovaTealLight)))
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.statusBarsPadding().padding(8.dp)
            ) {
                Icon(Icons.Filled.ArrowBack, "Back", tint = Color.White)
            }
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("🛍️", fontSize = 52.sp)
                Spacer(Modifier.height(8.dp))
                Text("ShopNova", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.ExtraBold)
                Text("Your universe of deals", color = Color.White.copy(0.8f), fontSize = 14.sp)
            }
        }

        // Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .offset(y = (-30).dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
                .border(1.dp, NovaDivider, RoundedCornerShape(24.dp))
                .padding(24.dp)
        ) {
            Column {
                // Toggle tabs
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(NovaLightGrey)
                        .padding(4.dp)
                ) {
                    listOf("Sign In", "Sign Up").forEachIndexed { idx, label ->
                        val sel = (idx == 1) == isSignUp
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (sel) NovaDeepTeal else Color.Transparent)
                                .clickable { isSignUp = idx == 1; errorMsg = "" }
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                label,
                                color = if (sel) Color.White else NovaGrey,
                                fontWeight = if (sel) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 15.sp
                            )
                        }
                    }
                }

                Spacer(Modifier.height(20.dp))

                if (isSignUp) {
                    NovaTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = "Full Name",
                        icon = Icons.Filled.Person
                    )
                    Spacer(Modifier.height(12.dp))
                    NovaTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = "Phone Number",
                        icon = Icons.Filled.Phone,
                        keyboardType = KeyboardType.Phone
                    )
                    Spacer(Modifier.height(12.dp))
                }

                NovaTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email Address",
                    icon = Icons.Filled.Email,
                    keyboardType = KeyboardType.Email
                )
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    leadingIcon = { Icon(Icons.Filled.Lock, null, tint = NovaDeepTeal) },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                null, tint = NovaGrey
                            )
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NovaDeepTeal,
                        unfocusedBorderColor = NovaDivider
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )

                if (!isSignUp) {
                    TextButton(
                        onClick = {},
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Forgot Password?", color = NovaDeepTeal, fontSize = 13.sp)
                    }
                }

                if (errorMsg.isNotBlank()) {
                    Spacer(Modifier.height(8.dp))
                    Text(errorMsg, color = NovaRed, fontSize = 13.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                }

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = {
                        errorMsg = ""
                        val n = if (isSignUp) name else "User"
                        val p = if (isSignUp) phone else ""
                        val success = viewModel.login(n, email, p, password)
                        if (success) onLoginSuccess()
                        else errorMsg = if (email.isBlank()) "Please enter your email"
                        else if (password.length < 6) "Password must be at least 6 characters"
                        else "Invalid credentials. Please try again."
                    },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NovaDeepTeal)
                ) {
                    Text(
                        if (isSignUp) "Create Account" else "Sign In",
                        fontWeight = FontWeight.Bold, fontSize = 16.sp
                    )
                }

                Spacer(Modifier.height(16.dp))

                // Divider
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Divider(modifier = Modifier.weight(1f), color = NovaDivider)
                    Text("  or continue with  ", color = NovaGrey, fontSize = 12.sp)
                    Divider(modifier = Modifier.weight(1f), color = NovaDivider)
                }

                Spacer(Modifier.height(16.dp))

                // Social buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SocialButton("G  Google", modifier = Modifier.weight(1f))
                    SocialButton("f  Facebook", modifier = Modifier.weight(1f))
                }
            }
        }

        // Bottom text
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                if (isSignUp) "Already have an account? " else "Don't have an account? ",
                color = NovaGrey, fontSize = 14.sp
            )
            Text(
                if (isSignUp) "Sign In" else "Sign Up",
                color = NovaDeepTeal,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.clickable { isSignUp = !isSignUp; errorMsg = "" }
            )
        }
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
fun NovaTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(icon, null, tint = NovaDeepTeal) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = NovaDeepTeal,
            unfocusedBorderColor = NovaDivider
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true
    )
}

@Composable
fun SocialButton(label: String, modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = {},
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, NovaDivider)
    ) {
        Text(label, color = NovaOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Medium)
    }
}
