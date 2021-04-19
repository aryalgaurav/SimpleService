package com.sample.shopping.simpleorderservice.client

import org.springframework.stereotype.Service
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Service
class MailClient {

    val threadPool: ExecutorService = Executors.newCachedThreadPool()

    fun sendCustomerNotification() {
        threadPool.submit { notifyCustomer("Your order is ready.") }
    }

    /* We can build this async functionality as we like. This is only a basic/simple implementation
    of a messaging where a println message does the notification job and the service
    is not waiting. */
    private fun notifyCustomer(message: String) {
        Thread.sleep(5000)
        println(message)
    }
}
