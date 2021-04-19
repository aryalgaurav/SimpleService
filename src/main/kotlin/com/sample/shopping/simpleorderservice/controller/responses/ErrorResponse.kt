package com.sample.shopping.simpleorderservice.controller.responses

import java.util.Calendar

class ErrorResponse(code: String, message: String, rootCause: List<ErrorCause>) {
    val errors = AppError(code, message, rootCause)
}

data class AppError(
    val code: String,
    val reason: String,
    val rootCause: List<ErrorCause>,
    val dateTime: DateTime = DateTime()
)

data class ErrorCause(
    val code: String,
    val reason: String
)

data class DateTime(
    val value: Long = System.currentTimeMillis(),
    val timeZone: String = Calendar.getInstance().timeZone.displayName
)