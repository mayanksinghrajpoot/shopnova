package com.shopnova.navigation

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object Login : Screen("login")
    object ProductDetail : Screen("product/{productId}") {
        fun createRoute(productId: Int) = "product/$productId"
    }
    object Billing : Screen("billing/{productId}/{quantity}") {
        fun createRoute(productId: Int, quantity: Int = 1) = "billing/$productId/$quantity"
    }
    object OrderSuccess : Screen("order_success")
}
