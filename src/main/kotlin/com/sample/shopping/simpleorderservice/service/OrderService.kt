package com.sample.shopping.simpleorderservice.service

import com.sample.shopping.simpleorderservice.controller.models.requests.ItemDetail
import com.sample.shopping.simpleorderservice.controller.models.requests.ItemName
import com.sample.shopping.simpleorderservice.controller.models.requests.ItemName.APPLE
import com.sample.shopping.simpleorderservice.controller.models.requests.ItemName.BANANA
import com.sample.shopping.simpleorderservice.controller.models.requests.ItemName.PINEAPPLE
import com.sample.shopping.simpleorderservice.controller.responses.ItemsCostResponse
import org.springframework.stereotype.Service

@Service
class OrderService {

    fun calculateOrderCost(itemDetails: List<ItemDetail>): ItemsCostResponse {
        var totalCost = 0.0F
        itemDetails.forEach { item ->
            totalCost += item.name.price.toFloat() * item.quantity
        }
        val totalDiscount = calculateDiscountOffers(itemDetails)
        return ItemsCostResponse(
            finalCost = String.format("%.2f", totalCost - totalDiscount),
            totalDiscount = String.format("%.2f", totalDiscount)
        )
    }

    private fun calculateDiscountOffers(itemDetails: List<ItemDetail>): Float {
        var totalDiscount = 0.0F
        itemDetails.forEach { item ->
            totalDiscount += when (item.name) {
                // BOGO on Apples.
                APPLE -> {
                    val applicableApplesDiscount = item.quantity / 2
                    applicableApplesDiscount * item.name.price.toFloat()
                }
                // 3 for the price of 2. Each 3rd ORANGE is free.
                ItemName.ORANGE -> {
                    val applicableOrangesDiscount = item.quantity / 3
                    applicableOrangesDiscount * item.name.price.toFloat()
                }
                // following discounts to not exist for the requirements of this service.
                BANANA -> TODO()
                PINEAPPLE -> TODO()
            }
        }
        return totalDiscount
    }
}