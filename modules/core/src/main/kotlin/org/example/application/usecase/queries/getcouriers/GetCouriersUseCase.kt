package org.example.application.usecase.queries.getcouriers

import org.example.domain.model.courieraggregate.Courier
import org.example.ports.CourierRepository

class GetCouriersUseCase(
    private val courierRepository: CourierRepository
) {
    fun execute(): GetCouriersResponse {
        val busyCouriers = courierRepository.getBusyCouriers()
        return mapToResponse(busyCouriers)
    }

    private fun mapToResponse(couriers: List<Courier>): GetCouriersResponse {
        val courierResponses = couriers.map { courier ->
            GetCouriersResponse.Courier(
                id = courier.id,
                name = courier.name,
                location = GetCouriersResponse.Courier.Location(
                    x = courier.location.x,
                    y = courier.location.y
                ),
                transportId = courier.transport.id
            )
        }
        return GetCouriersResponse(couriers = courierResponses)
    }
}