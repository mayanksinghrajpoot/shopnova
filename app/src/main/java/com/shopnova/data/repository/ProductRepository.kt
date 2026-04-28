package com.shopnova.data.repository

import com.shopnova.data.model.Category
import com.shopnova.data.model.Product

object ProductRepository {

    val categories = listOf(
        Category(1, "Electronics", "📱", 0xFF1565C0),
        Category(2, "Fashion", "👗", 0xFF6A1B9A),
        Category(3, "Home", "🏠", 0xFF2E7D32),
        Category(4, "Sports", "⚽", 0xFFE65100),
        Category(5, "Books", "📚", 0xFF4E342E),
        Category(6, "Beauty", "💄", 0xFFC62828),
        Category(7, "Grocery", "🛒", 0xFF558B2F),
        Category(8, "Toys", "🧸", 0xFFF57F17),
    )

    val allProducts = listOf(
        // Electronics - Smartphones
        Product(
            id = 1, name = "Samsung Galaxy S25 Ultra", brand = "Samsung",
            price = 129999.0, originalPrice = 139999.0,
            rating = 4.7f, reviewCount = 8421,
            imageUrl = "https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=400",
            category = "Electronics",
            description = "Samsung's most powerful phone with a built-in S Pen, 200MP camera, Snapdragon 8 Elite chip, and 5000mAh battery. The ultimate Android flagship.",
            features = listOf("200MP Quad Camera", "Snapdragon 8 Elite", "Built-in S Pen", "5000mAh Battery", "12GB RAM + 256GB", "6.9\" QHD+ 120Hz"),
            badge = "Best Seller", deliveryDays = 1
        ),
        Product(
            id = 2, name = "Apple iPhone 16 Pro Max", brand = "Apple",
            price = 159900.0, originalPrice = 164900.0,
            rating = 4.8f, reviewCount = 12034,
            imageUrl = "https://images.unsplash.com/photo-1592750475338-74b7b21085ab?w=400",
            category = "Electronics",
            description = "Powered by A18 Pro chip, featuring a 48MP Fusion camera system, titanium design, and Action Button. The most advanced iPhone ever.",
            features = listOf("A18 Pro Chip", "48MP Fusion Camera", "Titanium Design", "Action Button", "USB-C 3.0", "ProMotion 120Hz"),
            badge = "New", deliveryDays = 1
        ),
        Product(
            id = 3, name = "OnePlus 13 5G", brand = "OnePlus",
            price = 69999.0, originalPrice = 79999.0,
            rating = 4.5f, reviewCount = 4231,
            imageUrl = "https://images.unsplash.com/photo-1598327105666-5b89351aff97?w=400",
            category = "Electronics",
            description = "Flagship killer with Snapdragon 8 Elite, Hasselblad camera system, 100W SUPERVOOC charging, and a stunning 6.82\" AMOLED display.",
            features = listOf("Snapdragon 8 Elite", "Hasselblad Camera", "100W SUPERVOOC", "6000mAh Battery", "6.82\" AMOLED", "16GB RAM"),
            badge = "Sale", deliveryDays = 1
        ),
        // Electronics - Laptops
        Product(
            id = 4, name = "Apple MacBook Air M3 13\"", brand = "Apple",
            price = 114900.0, originalPrice = 124900.0,
            rating = 4.8f, reviewCount = 6721,
            imageUrl = "https://images.unsplash.com/photo-1496181133206-80ce9b88a853?w=400",
            category = "Electronics",
            description = "Supercharged by M3 chip with up to 18 hours battery life, fanless design, 13.6\" Liquid Retina display, and all-day performance.",
            features = listOf("Apple M3 Chip", "18hr Battery Life", "13.6\" Liquid Retina", "8GB Unified Memory", "256GB SSD", "Fanless Design"),
            badge = "Best Seller", deliveryDays = 2
        ),
        Product(
            id = 5, name = "Dell XPS 15 (2024)", brand = "Dell",
            price = 189990.0, originalPrice = 209990.0,
            rating = 4.6f, reviewCount = 2134,
            imageUrl = "https://images.unsplash.com/photo-1593642632559-0c6d3fc62b89?w=400",
            category = "Electronics",
            description = "Premium 15.6\" OLED laptop with Intel Core Ultra 9, NVIDIA RTX 4070, and a stunning InfinityEdge display. Built for creators.",
            features = listOf("Intel Core Ultra 9", "NVIDIA RTX 4070", "15.6\" OLED 4K", "32GB DDR5", "1TB SSD", "Thunderbolt 4"),
            badge = "New", deliveryDays = 3
        ),
        // Electronics - Audio
        Product(
            id = 6, name = "Sony WH-1000XM5", brand = "Sony",
            price = 24990.0, originalPrice = 34990.0,
            rating = 4.7f, reviewCount = 15432,
            imageUrl = "https://images.unsplash.com/photo-1590658268037-6bf12165a8df?w=400",
            category = "Electronics",
            description = "Industry-leading noise cancelling headphones with 30hr battery, crystal clear hands-free calling, and multipoint connection.",
            features = listOf("Industry-Leading ANC", "30hr Battery", "Multipoint Connection", "Speak-to-Chat", "LDAC Hi-Res", "Foldable Design"),
            badge = "Best Seller", deliveryDays = 1
        ),
        Product(
            id = 7, name = "Apple AirPods Pro (2nd Gen)", brand = "Apple",
            price = 24900.0, originalPrice = 26900.0,
            rating = 4.6f, reviewCount = 9871,
            imageUrl = "https://images.unsplash.com/photo-1603351154351-5e2d0600bb77?w=400",
            category = "Electronics",
            description = "Active Noise Cancellation, Adaptive Audio, Transparency mode, and Personalized Spatial Audio. Up to 30 hours total listening time.",
            features = listOf("Active Noise Cancellation", "Adaptive Audio", "Spatial Audio", "30hr Total Battery", "MagSafe Charging", "IP54 Water Resistant"),
            deliveryDays = 1
        ),
        Product(
            id = 8, name = "JBL Flip 7 Bluetooth Speaker", brand = "JBL",
            price = 11999.0, originalPrice = 14999.0,
            rating = 4.5f, reviewCount = 6234,
            imageUrl = "https://images.unsplash.com/photo-1608043152269-423dbba4e7e1?w=400",
            category = "Electronics",
            description = "Portable waterproof speaker with 12 hours of playtime, PartyBoost to connect multiple speakers, and bold JBL Pro Sound.",
            features = listOf("IP67 Waterproof", "12hr Battery", "PartyBoost", "USB-C Charging", "JBL Pro Sound", "Portable Design"),
            badge = "Sale", deliveryDays = 2
        ),
        // Electronics - Cameras & Wearables
        Product(
            id = 9, name = "GoPro HERO13 Black", brand = "GoPro",
            price = 39990.0, originalPrice = 44990.0,
            rating = 4.5f, reviewCount = 3421,
            imageUrl = "https://images.unsplash.com/photo-1502920917128-1aa500764cbd?w=400",
            category = "Electronics",
            description = "5.3K60 video, HyperSmooth 6.0 stabilization, waterproof to 33ft, and Emmy Award-winning performance. Built for your adventure.",
            features = listOf("5.3K60 Video", "HyperSmooth 6.0", "27MP Photo", "33ft Waterproof", "Live Streaming", "Voice Control"),
            deliveryDays = 3
        ),
        Product(
            id = 10, name = "Apple Watch Series 10", brand = "Apple",
            price = 46900.0, originalPrice = 49900.0,
            rating = 4.7f, reviewCount = 7823,
            imageUrl = "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=400",
            category = "Electronics",
            description = "Thinnest Apple Watch ever. Sleep apnea detection, ECG, blood oxygen, crash detection, and all-day fitness tracking.",
            features = listOf("Sleep Apnea Detection", "ECG App", "Blood Oxygen", "Crash Detection", "WatchOS 11", "18hr Battery"),
            badge = "New", deliveryDays = 1
        ),
        Product(
            id = 11, name = "Samsung Galaxy Watch 7", brand = "Samsung",
            price = 32999.0, originalPrice = 38999.0,
            rating = 4.5f, reviewCount = 4123,
            imageUrl = "https://images.unsplash.com/photo-1617043786394-f977fa12eddf?w=400",
            category = "Electronics",
            description = "Advanced health monitoring with BioActive Sensor, AI-powered insights, 40hr battery, and Google apps built-in.",
            features = listOf("BioActive Sensor", "Body Composition", "AI Energy Score", "40hr Battery", "Google Maps", "Wear OS 5"),
            badge = "Sale", deliveryDays = 2
        ),
        // Fashion
        Product(
            id = 12, name = "Levi's 511 Slim Fit Jeans", brand = "Levi's",
            price = 2999.0, originalPrice = 4499.0,
            rating = 4.4f, reviewCount = 12341,
            imageUrl = "https://images.unsplash.com/photo-1542272604-787c3835535d?w=400",
            category = "Fashion",
            description = "Classic slim fit jeans made from stretch denim. Sits below the waist with a slim leg from hip to ankle.",
            features = listOf("Slim Fit", "Stretch Denim", "Sits Below Waist", "Machine Washable", "Sizes 28-40"),
            badge = "Best Seller", deliveryDays = 3
        ),
        Product(
            id = 13, name = "Nike Air Max 270", brand = "Nike",
            price = 12995.0, originalPrice = 15995.0,
            rating = 4.6f, reviewCount = 8921,
            imageUrl = "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=400",
            category = "Fashion",
            description = "Nike's tallest Air unit yet for an incredibly soft ride. Mesh upper, foam midsole, and the iconic 270-degree Air window.",
            features = listOf("270 Air Unit", "Mesh Upper", "Foam Midsole", "Rubber Outsole", "Sizes 6-12", "Unisex"),
            badge = "Sale", deliveryDays = 2
        ),
        // Home
        Product(
            id = 14, name = "Dyson V15 Detect Vacuum", brand = "Dyson",
            price = 59900.0, originalPrice = 69900.0,
            rating = 4.7f, reviewCount = 3421,
            imageUrl = "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=400",
            category = "Home",
            description = "Laser reveals microscopic dust. Acoustic piezo sensor counts and measures particles and displays results on LCD screen.",
            features = listOf("Laser Dust Detection", "240 AW Suction", "60min Runtime", "HEPA Filtration", "LCD Screen", "7 Attachments"),
            badge = "Best Seller", deliveryDays = 2
        ),
        // Sports
        Product(
            id = 15, name = "Garmin Forerunner 965", brand = "Garmin",
            price = 59990.0, originalPrice = 64990.0,
            rating = 4.8f, reviewCount = 2134,
            imageUrl = "https://images.unsplash.com/photo-1576243345690-4e4b79b63288?w=400",
            category = "Sports",
            description = "Premium GPS running watch with AMOLED display, Training Readiness score, race predictor, and up to 31 days battery.",
            features = listOf("AMOLED Display", "31-Day Battery", "Training Readiness", "Race Predictor", "Multisport Tracking", "Music Storage"),
            badge = "New", deliveryDays = 3
        ),
    )

    fun searchProducts(query: String): List<Product> {
        if (query.isBlank()) return allProducts
        val terms = query.lowercase().trim().split("\\s+".toRegex())
        return allProducts.filter { p ->
            val searchable = listOf(
                p.name, p.brand, p.category, p.description
            ).joinToString(" ").lowercase() + " " + p.features.joinToString(" ").lowercase()
            // All typed words must match somewhere in the product text
            terms.all { term -> searchable.contains(term) }
        }.sortedByDescending { p ->
            val q = query.lowercase()
            when {
                p.name.lowercase().contains(q) -> 3
                p.brand.lowercase().contains(q) -> 2
                else -> 1
            }
        }
    }

    fun getProductsByCategory(category: String): List<Product> {
        return allProducts.filter { it.category == category }
    }

    fun getProductById(id: Int): Product? = allProducts.find { it.id == id }

    fun getBestSellers(): List<Product> = allProducts.filter { it.badge == "Best Seller" }

    fun getDealsOfDay(): List<Product> = allProducts.filter { it.discountPercent >= 20 }
}