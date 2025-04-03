package org.example.application.usecase.queries.getcouriers

import java.util.UUID

data class GetCouriersResponse(
    val couriers: List<Courier>
) {
    data class Courier(
        val id: UUID,
        val name: String,
        val location: Location,
        val transportId: UUID
    ) {
        data class Location(
            val x: Int,
            val y: Int
        )
    }
}
