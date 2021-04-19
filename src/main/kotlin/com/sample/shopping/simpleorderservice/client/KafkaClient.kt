package com.sample.shopping.simpleorderservice.client

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaClient {
    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, String>
    private val topic = "KAFKA_TOPIC"

    // We can publish following the avro schema. For this project, I have not used it. Just a basic string message.
    fun publishMessage(totalCost: Float, totalDiscount: Float) {
        kafkaTemplate.send(topic, "The total cost is $totalCost and the total discount for the order is $totalDiscount")
    }
}