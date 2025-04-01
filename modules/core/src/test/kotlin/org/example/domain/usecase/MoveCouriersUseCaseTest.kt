package org.example.domain.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.application.usecase.commands.movecouriers.MoveCouriersUseCase
import org.example.domain.model.courieraggregate.Courier
import org.example.domain.model.orderaggregate.Order
import org.example.domain.model.sharedkernel.Location
import org.example.ports.CourierRepository
import org.example.ports.OrderRepository
import org.junit.jupiter.api.Test
import java.util.UUID

class MoveCouriersUseCaseTest {

    private val courierRepository: CourierRepository = mockk(relaxed = true)
    private val orderRepository: OrderRepository = mockk(relaxed = true)
    private val useCase = MoveCouriersUseCase(courierRepository, orderRepository)

    @Test
    fun `should move couriers and complete orders when orders are assigned`() {
        // Arrange
        val courierId = UUID.randomUUID()
        val orderId = UUID.randomUUID()
        val location = Location.create(1, 1)

        val order = Order.create(orderId, location).assign(courierId)
        val courier = Courier.create("name", "nameTransport", 1, Location.create(1, 1))

        every { orderRepository.getAssignedOrders() } returns listOf(order)
        every { courierRepository.getCourier(courierId) } returns courier

        // Act
        useCase.execute()

        // Assert
        verify { orderRepository.getAssignedOrders() }
        verify { courierRepository.getCourier(courierId) }

        val updatedCourier = courier.move(location)
        verify { courierRepository.upsertCourier(updatedCourier.setStatusFree()) }
        verify { orderRepository.upsertOrder(order.complete()) }
    }

    @Test
    fun `should not move couriers when no orders are assigned`() {
        // Arrange
        every { orderRepository.getAssignedOrders() } returns emptyList()

        // Act
        useCase.execute()

        // Assert
        verify { orderRepository.getAssignedOrders() }
        verify(exactly = 0) { courierRepository.getCourier(any()) }
        verify(exactly = 0) { courierRepository.upsertCourier(any()) }
        verify(exactly = 0) { orderRepository.upsertOrder(any()) }
    }

    @Test
    fun `should not move couriers when courier is not found`() {
        // Arrange
        val courierId = UUID.randomUUID()
        val orderId = UUID.randomUUID()
        val location = Location.create(1, 1)

        val order = Order.create(orderId, location).assign(courierId)

        every { orderRepository.getAssignedOrders() } returns listOf(order)
        every { courierRepository.getCourier(courierId) } returns null

        // Act
        useCase.execute()

        // Assert
        verify { orderRepository.getAssignedOrders() }
        verify { courierRepository.getCourier(courierId) }
        verify(exactly = 0) { courierRepository.upsertCourier(any()) }
        verify(exactly = 0) { orderRepository.upsertOrder(any()) }
    }

    @Test
    fun `should not complete order when courier does not reach the location`() {
        // Arrange
        val courierId = UUID.randomUUID()
        val orderId = UUID.randomUUID()
        val orderLocation = Location.create(2, 2)
        val courierLocation = Location.create(1, 1)

        val order = Order.create(orderId, orderLocation).assign(courierId)
        val courier = Courier.create("name", "nameTransport", 1, Location.create(1, 1))

        every { orderRepository.getAssignedOrders() } returns listOf(order)
        every { courierRepository.getCourier(courierId) } returns courier

        // Act
        useCase.execute()

        // Assert
        verify { orderRepository.getAssignedOrders() }
        verify { courierRepository.getCourier(courierId) }

        val updatedCourier = courier.move(orderLocation)
        verify { courierRepository.upsertCourier(updatedCourier) }
        verify(exactly = 0) { orderRepository.upsertOrder(any()) }
    }
}