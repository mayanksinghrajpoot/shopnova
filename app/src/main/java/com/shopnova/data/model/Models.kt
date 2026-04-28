package com.shopnova.data.model

data class Product(
    val id: Int,
    val name: String,
    val brand: String,
    val price: Double,
    val originalPrice: Double,
    val rating: Float,
    val reviewCount: Int,
    val imageUrl: String,
    val category: String,
    val description: String,
    val features: List<String>,
    val inStock: Boolean = true,
    val badge: String? = null, // e.g. "Best Seller", "New", "Sale"
    val deliveryDays: Int = 2
) {
    val discountPercent: Int
        get() = if (originalPrice > price)
            ((originalPrice - price) / originalPrice * 100).toInt()
        else 0
}

data class Category(
    val id: Int,
    val name: String,
    val icon: String,
    val color: Long
)

data class CartItem(
    val product: Product,
    val quantity: Int
)

data class User(
    val name: String,
    val email: String,
    val phone: String,
    val address: String = ""
)

data class Order(
    val items: List<CartItem>,
    val deliveryAddress: String,
    val paymentMethod: String,
    val totalAmount: Double
)
