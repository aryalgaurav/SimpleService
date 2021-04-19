package com.sample.shopping.simpleorderservice.exceptions

import java.lang.RuntimeException

class BadRequestException(message: String?) : RuntimeException(message)