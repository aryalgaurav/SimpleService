package com.sample.shopping.simpleorderservice.controller.models.requests

// Each item name has the price associated with it.
enum class ItemName(val price: String) {
    APPLE("0.6"),
    BANANA("0.1"),
    ORANGE("0.25"),
    PINEAPPLE("1.00"),
}