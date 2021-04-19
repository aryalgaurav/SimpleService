package com.sample.shopping.simpleorderservice.controller

import com.sample.shopping.simpleorderservice.controller.models.requests.ItemsRequest
import com.sample.shopping.simpleorderservice.controller.responses.ItemsCostResponse
import com.sample.shopping.simpleorderservice.service.OrderService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@Validated
@RequestMapping("v1/order")
class OrderController(private val orderService: OrderService) {

    @PostMapping(value = ["/cost"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun calculateOrderCost(@Valid @RequestBody request: ItemsRequest): ResponseEntity<ItemsCostResponse> {
        return ResponseEntity(
            orderService.calculateOrderCost(request.itemDetails!!),
            HttpStatus.OK
        )
    }
}
