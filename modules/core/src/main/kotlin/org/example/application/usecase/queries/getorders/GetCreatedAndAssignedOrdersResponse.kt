package org.example.application.usecase.queries.getorders

import java.util.UUID

data class GetCreatedAndAssignedOrdersResponse(
    val orders: List<Order>,
) {
    data class Order(
        val id: UUID,
        val location: Location
    ) {
        data class Location(
            val x: Int,
            val y: Int
        )
    }
}
