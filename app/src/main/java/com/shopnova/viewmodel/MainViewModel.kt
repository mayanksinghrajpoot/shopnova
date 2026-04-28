package com.shopnova.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.shopnova.data.model.CartItem
import com.shopnova.data.model.Product
import com.shopnova.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    // Auth state
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    // Cart state
    val cartItems = mutableStateListOf<CartItem>()

    val cartCount: Int get() = cartItems.sumOf { it.quantity }
    val cartTotal: Double get() = cartItems.sumOf { it.product.price * it.quantity }

    // Wishlist
    val wishlistIds = mutableStateListOf<Int>()

    fun login(name: String, email: String, phone: String, password: String): Boolean {
        // Simple mock validation
        if (email.isNotBlank() && password.length >= 6) {
            _currentUser.value = User(name = name, email = email, phone = phone)
            _isLoggedIn.value = true
            return true
        }
        return false
    }

    fun logout() {
        _isLoggedIn.value = false
        _currentUser.value = null
        cartItems.clear()
        wishlistIds.clear()
    }

    fun addToCart(product: Product, quantity: Int = 1) {
        val existing = cartItems.indexOfFirst { it.product.id == product.id }
        if (existing >= 0) {
            cartItems[existing] = cartItems[existing].copy(
                quantity = cartItems[existing].quantity + quantity
            )
        } else {
            cartItems.add(CartItem(product, quantity))
        }
    }

    fun removeFromCart(productId: Int) {
        cartItems.removeIf { it.product.id == productId }
    }

    fun toggleWishlist(productId: Int) {
        if (wishlistIds.contains(productId)) {
            wishlistIds.remove(productId)
        } else {
            wishlistIds.add(productId)
        }
    }

    fun isWishlisted(productId: Int) = wishlistIds.contains(productId)

    fun clearCart() {
        cartItems.clear()
    }
}
