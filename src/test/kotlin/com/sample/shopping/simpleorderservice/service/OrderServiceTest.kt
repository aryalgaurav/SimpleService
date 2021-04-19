package com.sample.shopping.simpleorderservice.service

import com.sample.shopping.simpleorderservice.controller.models.requests.ItemName
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import util.OrderServiceTestUtil.Companion.getTestItemDetail

class OrderServiceTest {

    private val orderService = OrderService()

    @Test
    fun `should apply BOGO on apples`() {
        // request -> a total of 5 apples.
        // except the response to BOGO for 4 apples.
        var itemDetails = listOf(getTestItemDetail())

        val response = orderService.calculateOrderCost(itemDetails)
        assertEquals("1.80", response.finalCost) // out of 5, cost is only for 3.
        assertEquals("1.20", response.totalDiscount) // BOGO discount will be 2 out of 5 = 1.20 total
    }

    @Test
    fun `should apply no discount on only 1 apple`() {
        // request -> a total of 1 apple.
        // except the response to have no discounts.
        var itemDetails = listOf(getTestItemDetail(quantity = 1))

        val response = orderService.calculateOrderCost(itemDetails)
        assertEquals("0.60", response.finalCost)
        assertEquals("0.00", response.totalDiscount)
    }

    @Test
    fun `should apply no discount on only 2 oranges`() {
        // request -> a total of 2 oranges.
        // except the response to have no discounts.
        var itemDetails = listOf(getTestItemDetail(quantity = 2, name = ItemName.ORANGE))

        val response = orderService.calculateOrderCost(itemDetails)
        assertEquals("0.50", response.finalCost)
        assertEquals("0.00", response.totalDiscount)
    }

    @Test
    fun `should apply price of 4 oranges on a quantity of 6`() {
        // request -> a total of 6 oranges.
        // except the response to have no discounts.
        var itemDetails = listOf(getTestItemDetail(quantity = 6, name = ItemName.ORANGE))

        val response = orderService.calculateOrderCost(itemDetails)
        assertEquals("1.00", response.finalCost)
        assertEquals("0.50", response.totalDiscount) // discount of 2 on a total of 6
    }
}
