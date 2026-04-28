package com.shopnova.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.shopnova.ui.screens.billing.BillingScreen
import com.shopnova.ui.screens.billing.OrderSuccessScreen
import com.shopnova.ui.screens.dashboard.DashboardScreen
import com.shopnova.ui.screens.login.LoginScreen
import com.shopnova.ui.screens.product.ProductDetailScreen
import com.shopnova.viewmodel.MainViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route
    ) {
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                viewModel = viewModel,
                onProductClick = { productId ->
                    navController.navigate(Screen.ProductDetail.createRoute(productId))
                },
                onLoginClick = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = {
                    navController.popBackStack()
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: return@composable
            ProductDetailScreen(
                productId = productId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onBuyNow = { quantity ->
                    navController.navigate(Screen.Billing.createRoute(productId, quantity))
                },
                onLoginRequired = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }

        composable(
            route = Screen.Billing.route,
            arguments = listOf(
                navArgument("productId") { type = NavType.IntType },
                navArgument("quantity") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: return@composable
            val quantity = backStackEntry.arguments?.getInt("quantity") ?: 1
            BillingScreen(
                productId = productId,
                quantity = quantity,
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onOrderPlaced = {
                    navController.navigate(Screen.OrderSuccess.route) {
                        popUpTo(Screen.Dashboard.route)
                    }
                }
            )
        }

        composable(Screen.OrderSuccess.route) {
            OrderSuccessScreen(
                onContinueShopping = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Dashboard.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
