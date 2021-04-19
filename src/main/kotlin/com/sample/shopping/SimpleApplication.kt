package com.sample.shopping

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties
class SimpleApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<SimpleApplication>(*args)
        }
    }
}