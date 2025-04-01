package org.example.domain.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.application.usecase.commands.assignorders.AssignOrdersUseCase
import org.example.domain.model.courieraggregate.Courier
import org.example.domain.model.orderaggregate.Order
import org.example.domain.model.sharedkernel.Location
import org.example.domain.services.DispatchService
import org.example.ports.CourierRepository
import org.example.ports.OrderRepository
import org.junit.jupiter.api.Test
import java.util.UUID

class AssignOrdersUseCaseTest {

    private val courierRepository: CourierRepository = mockk(relaxed = true)
    private val orderRepository: OrderRepository = mockk(relaxed = true)
    private val dispatchService: DispatchService = mockk(relaxed = true)

    private val useCase = AssignOrdersUseCase(courierRepository, orderRepository, dispatchService)

    @Test
    fun `should assign order to courier when there are free couriers`() {
        // Arrange
        val orderId = UUID.randomUUID()
        val courierId = UUID.randomUUID()
        val location = Location.create(1, 1)
        val order = Order.create(orderId, location)
        val courier = Courier.create("Courier", "Bike", 3, location).setStatusFree()

        every { orderRepository.getCreatedOrder() } returns order
        every { courierRepository.getFreeCouriers() } returns listOf(courier)
        every { dispatchService.dispatch(order, listOf(courier)) } returns courier.setStatusBusy()

        // Act
        useCase.execute()

        // Assert
        verify { orderRepository.upsertOrder(order.assign(courierId)) }
        verify { courierRepository.upsertCourier(courier.setStatusBusy()) }
    }

    @Test
    fun `should not assign order when there are no free couriers`() {
        // Arrange
        val order = Order.create(UUID.randomUUID(), Location.create(1, 1))

        every { orderRepository.getCreatedOrder() } returns order
        every { courierRepository.getFreeCouriers() } returns emptyList()

        // Act
        useCase.execute()

        // Assert
        verify(exactly = 0) { orderRepository.upsertOrder(any()) }
        verify(exactly = 0) { courierRepository.upsertCourier(any()) }
    }

    @Test
    fun `should not assign order when there is no created order`() {
        // Arrange
        every { orderRepository.getCreatedOrder() } returns null

        // Act
        useCase.execute()

        // Assert
        verify(exactly = 0) { orderRepository.upsertOrder(any()) }
        verify(exactly = 0) { courierRepository.upsertCourier(any()) }
    }
}