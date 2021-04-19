package com.sample.shopping.simpleorderservice.client

import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class StockClient {

    // possibly make a rest call to another service to see if the item is in stock
    // some other approach can be taken as well to solve this.
    // in a microservice architecture, making a rest call to the StockService would be the way to go.
    fun isItemInStock(itemName: String): Boolean {
        var randomNumber = Random.nextInt(100)
        return randomNumber > 5 // just returning randomly. Ideally, another service call.
    }
}