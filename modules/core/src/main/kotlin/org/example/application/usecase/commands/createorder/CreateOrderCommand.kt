package org.example.application.usecase.commands.createorder

import java.util.UUID

data class CreateOrderCommand(
    val basketId: UUID,
    val street: String
) {
    init {
        require(street.isNotBlank()) { "Street must not be empty" }
    }
}
