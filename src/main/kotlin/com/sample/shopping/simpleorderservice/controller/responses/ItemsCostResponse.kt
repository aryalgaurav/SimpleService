package com.sample.shopping.simpleorderservice.controller.responses

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ItemsCostResponse(
    val finalCost: String,
    val totalDiscount: String
)
