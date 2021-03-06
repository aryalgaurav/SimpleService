package com.sample.shopping.simpleorderservice.service

// import com.sample.shopping.simpleorderservice.client.KafkaClient
import com.sample.shopping.simpleorderservice.client.MailClient
import com.sample.shopping.simpleorderservice.client.StockClient
import com.sample.shopping.simpleorderservice.controller.models.requests.ItemDetail
import com.sample.shopping.simpleorderservice.controller.models.requests.ItemName
import com.sample.shopping.simpleorderservice.controller.models.requests.ItemName.APPLE
import com.sample.shopping.simpleorderservice.controller.models.requests.ItemName.BANANA
import com.sample.shopping.simpleorderservice.controller.models.requests.ItemName.PINEAPPLE
import com.sample.shopping.simpleorderservice.controller.responses.ItemsCostResponse
import org.springframework.stereotype.Service

@Service
class OrderService(private val stockClient: StockClient) {

    fun calculateOrderCost(itemDetails: List<ItemDetail>): ItemsCostResponse {

        // this is a very basic case to handle out of stock items.
        // forcing isItemInStock() to take string param as another service will handle the out of stock items
        if (stockClient.isItemInStock(APPLE.toString()) && stockClient.isItemInStock(ItemName.ORANGE.toString())) {
            var totalCost = 0.0F
            itemDetails.forEach { item ->
                totalCost += item.name.price.toFloat() * item.quantity
            }
            val totalDiscount = calculateDiscountOffers(itemDetails)

            // Call kafka client to publish the messages to a kafka topic
            // Ideally, this should be an async call so that we do not fail or wait our service on the kafka message publishing.
            // Skipping that step/design for this task.

            // KafkaClient().publishMessage(totalCost, totalDiscount)
            MailClient().sendCustomerNotification()

            return ItemsCostResponse(
                finalCost = String.format("%.2f", totalCost - totalDiscount),
                totalDiscount = String.format("%.2f", totalDiscount)
            )
        } else {
            return ItemsCostResponse(
                finalCost = "0.00",
                totalDiscount = "0.00",
                status = "CANCELED - Out of stock",
                deliveryTime = null
            )
        }
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