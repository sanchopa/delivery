package org.example.domain.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.application.usecase.queries.getorders.GetCreatedAndAssignedOrdersUseCase
import org.example.domain.model.orderaggregate.Order
import org.example.domain.model.sharedkernel.Location
import org.example.ports.OrderRepository
import org.junit.jupiter.api.Test
import java.util.UUID

class GetCreatedAndAssignedOrdersUseCaseTest {

    private val orderRepository: OrderRepository = mockk(relaxed = true)
    private val useCase = GetCreatedAndAssignedOrdersUseCase(orderRepository)

    @Test
    fun `should return list of created and assigned orders`() {
        // Arrange
        val order1 = Order.create(UUID.randomUUID(), Location.create(1, 1))
        val order2 = Order.create(UUID.randomUUID(), Location.create(2, 2)).assign(UUID.randomUUID())

        every { orderRepository.getCreatedAndAssignedOrders() } returns listOf(order1, order2)

        // Act
        val response = useCase.execute()

        // Assert
        verify { orderRepository.getCreatedAndAssignedOrders() }
        assert(response.orders.size == 2)
        assert(response.orders[0].id == order1.id)
        assert(response.orders[0].location.x == order1.location.x)
        assert(response.orders[0].location.y == order1.location.y)

        assert(response.orders[1].id == order2.id)
        assert(response.orders[1].location.x == order2.location.x)
        assert(response.orders[1].location.y == order2.location.y)
    }

    @Test
    fun `should return empty list when no created and assigned orders`() {
        // Arrange
        every { orderRepository.getCreatedAndAssignedOrders() } returns emptyList()

        // Act
        val response = useCase.execute()

        // Assert
        verify { orderRepository.getCreatedAndAssignedOrders() }
        assert(response.orders.isEmpty())
    }
}