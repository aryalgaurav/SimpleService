package com.sample.shopping.simpleorderservice.configuration

import com.sample.shopping.simpleorderservice.controller.responses.ErrorCause
import com.sample.shopping.simpleorderservice.controller.responses.ErrorResponse
import com.sample.shopping.simpleorderservice.exceptions.BadRequestException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.server.ServerWebExchange

@RestController
@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler
    fun handle(ex: Throwable, exchange: ServerWebExchange): ErrorResponse {
        return when (ex) {
            is BadRequestException -> {
                exchange.response.statusCode = HttpStatus.BAD_REQUEST
                ErrorResponse(
                    HttpStatus.BAD_REQUEST.toString(), ex.message
                        ?: HttpStatus.BAD_REQUEST.toString(),
                    listOf(
                        ErrorCause(
                            HttpStatus.BAD_REQUEST.toString(), ex.message
                                ?: HttpStatus.BAD_REQUEST.toString())
                    )
                )
            }
            is WebExchangeBindException -> {
                exchange.response.statusCode = HttpStatus.BAD_REQUEST
                ErrorResponse(
                    HttpStatus.BAD_REQUEST.toString(), "Required data is missing",
                    ex.fieldErrors.map {
                        ErrorCause(
                            it.code!!, "${it.rejectedValue} is invalid for ${(it.field)}: ${it.defaultMessage}"
                        )
                    }
                )
            }
            else -> {
                throw ex
            }
        }
    }
}