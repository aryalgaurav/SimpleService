package com.sample.shopping.simpleorderservice.controller.models.requests

import org.hibernate.validator.constraints.Range
import javax.validation.Valid

data class ItemsRequest(
    @field:Valid
    val itemDetails: List<ItemDetail>? = null
)

// The validations can be different/more refined based on the requirements of the service
data class ItemDetail(
    val name: ItemName,

    // Let us assume that the customer cannot buy more than 1000 of the same item.
    @field:Range(min = 1, max = 1000)
    val quantity: Int
)
