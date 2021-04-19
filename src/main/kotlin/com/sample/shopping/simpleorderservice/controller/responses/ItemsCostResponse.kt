package com.sample.shopping.simpleorderservice.controller.responses

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.Instant
import java.time.temporal.ChronoUnit

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ItemsCostResponse(
    val finalCost: String,
    val totalDiscount: String,
    val status: String = "PENDING", // all orders are of PENDING status to start with
    val deliveryTime: Instant = Instant.now().plus(24L, ChronoUnit.HOURS) // assume all delivery times are 24 hours from now
)
