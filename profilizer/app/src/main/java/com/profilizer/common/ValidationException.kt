package com.profilizer.common

class ValidationException(override val message: String?) : RuntimeException(message) {
}