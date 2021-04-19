package com.sample.shopping.simpleorderservice.controller

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.whenever
import com.sample.shopping.simpleorderservice.controller.models.requests.ItemsRequest
import com.sample.shopping.simpleorderservice.controller.responses.ItemsCostResponse
import com.sample.shopping.simpleorderservice.service.OrderService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.ApplicationContext
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import util.OrderServiceTestUtil.Companion.getTestItemDetailRequest
import util.OrderServiceTestUtil.Companion.getTestItemsRequest
import javax.annotation.PostConstruct

private const val API_URL = "/v1/order/cost"

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderControllerTest {
    @MockBean
    private lateinit var orderService: OrderService

    @Autowired
    private lateinit var context: ApplicationContext
    private lateinit var webTestClient: WebTestClient

    @PostConstruct
    fun setupBefore() {
        webTestClient = WebTestClient
            .bindToApplicationContext(context)
            .configureClient()
            .build()
    }

    @BeforeEach
    fun setup() {
        Mockito.reset(orderService)
    }

    @Test
    fun `should return 400 response on invalid item quantity`() {
        val itemsRequest = getTestItemsRequest(
            itemDetails = listOf(
                getTestItemDetailRequest(quantity = -5)
            )
        )

        webTestClient.post()
            .uri(API_URL)
            .body(Mono.just(itemsRequest), ItemsRequest::class.java)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("errors.code")
            .isEqualTo("400 BAD_REQUEST")
            .jsonPath("errors.reason")
            .isEqualTo("Required data is missing")
    }

    // Only added unit test for invalid quantity.
    // Other controller, validation of request object unit tests can be added accordingly with the same approach above.

    /* Other in depth request body testing along with all combinations of valid and invalid data can be done via
    integration or SOA testing. Kotlin default value assignment helps us write unit tests very effectively without needing
    a lot of setup work like java.
    Leaving out some of the in depth testing to SOA/Integration testing here.

    Skipping the SOA testing for this task. But, that can be setup very easily with RestAssured framework residing in the
    same repo and as a separate gradle module.*/

    @Test
    fun `should return 200 response on valid request data`() {
        val itemsRequest = getTestItemsRequest()

        with(orderService) {
            whenever(
                calculateOrderCost(any())
            )doReturn(ItemsCostResponse(finalCost = "0.00", totalDiscount = "0.00"))

            webTestClient.post()
                .uri(API_URL)
                .body(Mono.just(itemsRequest), ItemsRequest::class.java)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("finalCost")
                .isEqualTo("0.00")
                .jsonPath("totalDiscount")
                .isEqualTo("0.00")
        }
    }
}
