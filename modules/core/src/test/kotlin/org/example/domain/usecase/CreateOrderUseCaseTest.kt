package org.example.domain.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.application.usecase.commands.createorder.CreateOrderCommand
import org.example.application.usecase.commands.createorder.CreateOrderUseCase
import org.example.domain.model.orderaggregate.Order
import org.example.domain.model.sharedkernel.Location
import org.example.ports.GeoService
import org.example.ports.OrderRepository
import org.junit.jupiter.api.Test
import java.util.UUID

class CreateOrderUseCaseTest {

    private val orderRepository: OrderRepository = mockk(relaxed = true)
    private val geoService: GeoService = mockk(relaxed = true)
    private val useCase = CreateOrderUseCase(orderRepository, geoService)

    @Test
    fun `should create order when basketId does not exist`() {
        // Arrange
        val basketId = UUID.randomUUID()
        val location = Location.createRandom()
        val order = Order.create(basketId, location)

        every { orderRepository.getOrder(basketId) } returns null

        // Act
        useCase.execute(CreateOrderCommand(basketId, "street"))

        // Assert
        verify { orderRepository.upsertOrder(order) }
    }

    @Test
    fun `should not create order when basketId already exists`() {
        // Arrange
        val basketId = UUID.randomUUID()
        val existingOrder = Order.create(basketId, Location.createRandom())

        every { orderRepository.getOrder(basketId) } returns existingOrder

        // Act
        useCase.execute(CreateOrderCommand(basketId, "street"))

        // Assert
        verify(exactly = 0) { orderRepository.upsertOrder(any()) }
    }
}