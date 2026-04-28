package com.shopnova.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// ShopNova Brand Colors - Deep Teal + Warm Amber
val NovaDeepTeal = Color(0xFF0D6E6E)
val NovaTealLight = Color(0xFF1A9696)
val NovaTealContainer = Color(0xFFB2DFDB)
val NovaAmber = Color(0xFFFF8F00)
val NovaAmberLight = Color(0xFFFFB300)
val NovaSurface = Color(0xFFF5F7F9)
val NovaBackground = Color(0xFFFFFFFF)
val NovaOnPrimary = Color(0xFFFFFFFF)
val NovaOnSurface = Color(0xFF1C1C1E)
val NovaGrey = Color(0xFF8E8E93)
val NovaLightGrey = Color(0xFFF2F2F7)
val NovaRed = Color(0xFFE53935)
val NovaGreen = Color(0xFF2E7D32)
val NovaDivider = Color(0xFFE5E5EA)
val NovaBadge = Color(0xFFFF3B30)

private val ShopNovaColorScheme = lightColorScheme(
    primary = NovaDeepTeal,
    onPrimary = NovaOnPrimary,
    primaryContainer = NovaTealContainer,
    secondary = NovaAmber,
    onSecondary = Color(0xFF1C1C1E),
    background = NovaBackground,
    surface = NovaSurface,
    onBackground = NovaOnSurface,
    onSurface = NovaOnSurface,
    error = NovaRed,
)

@Composable
fun ShopNovaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ShopNovaColorScheme,
        content = content
    )
}
