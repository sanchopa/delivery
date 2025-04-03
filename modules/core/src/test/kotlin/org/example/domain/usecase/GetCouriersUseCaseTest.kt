package org.example.domain.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.application.usecase.queries.getcouriers.GetCouriersUseCase
import org.example.domain.model.courieraggregate.Courier
import org.example.domain.model.sharedkernel.Location
import org.example.ports.CourierRepository
import org.junit.jupiter.api.Test

class GetCouriersUseCaseTest {

    private val courierRepository: CourierRepository = mockk(relaxed = true)
    private val useCase = GetCouriersUseCase(courierRepository)

    @Test
    fun `should return list of busy couriers`() {
        // Arrange
        val courier1 = Courier.create("Courier1", "Bike", 1, Location.create(1, 1)).setStatusBusy()
        val courier2 = Courier.create("Courier2", "Car", 3, Location.create(2, 2)).setStatusBusy()

        every { courierRepository.getBusyCouriers() } returns listOf(courier1, courier2)

        // Act
        val response = useCase.execute()

        // Assert
        verify { courierRepository.getBusyCouriers() }
        assert(response.couriers.size == 2)
        assert(response.couriers[0].id == courier1.id)
        assert(response.couriers[0].name == courier1.name)
        assert(response.couriers[0].location.x == courier1.location.x)
        assert(response.couriers[0].location.y == courier1.location.y)
        assert(response.couriers[0].transportId == courier1.transport.id)

        assert(response.couriers[1].id == courier2.id)
        assert(response.couriers[1].name == courier2.name)
        assert(response.couriers[1].location.x == courier2.location.x)
        assert(response.couriers[1].location.y == courier2.location.y)
        assert(response.couriers[1].transportId == courier2.transport.id)
    }

    @Test
    fun `should return empty list when no busy couriers`() {
        // Arrange
        every { courierRepository.getBusyCouriers() } returns emptyList()

        // Act
        val response = useCase.execute()

        // Assert
        verify { courierRepository.getBusyCouriers() }
        assert(response.couriers.isEmpty())
    }
}