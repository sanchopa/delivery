package org.example.postgres

import org.example.Generator
import org.example.TestConfig
import org.example.adapters.postgres.OrderDbRepository
import org.example.adapters.postgres.jpa.OrderJpaRepository
import org.example.adapters.postgres.mapper.OrderMapper
import org.example.config.InfraConfig
import org.example.container.PostgreSQLContainerInitializer
import org.example.domain.model.orderaggregate.Order
import org.example.domain.model.orderaggregate.OrderStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import java.util.UUID

@ContextConfiguration(
    classes = [TestConfig::class,
        InfraConfig::class,
        OrderJpaRepository::class,
        OrderDbRepository::class,
        OrderMapper::class],
    initializers = [PostgreSQLContainerInitializer::class]
)
class OrderDbRepositoryTest : AbstractRepositoryTest() {
    @Autowired
    lateinit var orderRepository: OrderDbRepository

    @Test
    fun `should save order`() {
        val order = Generator.createOrder()

        val res = orderRepository.upsertOrder(order)

        assertEquals(res, order)
    }

    @Test
    fun `should get order by id`() {
        val order = Generator.createOrder()
        orderRepository.upsertOrder(order)

        val retrievedOrder = orderRepository.getOrder(order.id)

        assertEquals(order, retrievedOrder)
    }

    @Test
    fun `should return null when order not found by id`() {
        val nonExistentOrderId = UUID.randomUUID()

        val retrievedOrder = orderRepository.getOrder(nonExistentOrderId)

        assertNull(retrievedOrder)
    }

    @Test
    fun `should get created order`() {
        val createdOrder = Generator.createOrder().copy(status = OrderStatus.CREATED)
        orderRepository.upsertOrder(createdOrder)

        val retrievedOrder = orderRepository.getCreatedOrder()

        assertEquals(createdOrder, retrievedOrder)
    }

    @Test
    fun `should return null when no created order found`() {
        val assignedOrder = Generator.createOrder().copy(status = OrderStatus.ASSIGNED)
        orderRepository.upsertOrder(assignedOrder)

        val retrievedOrder = orderRepository.getCreatedOrder()

        assertNull(retrievedOrder)
    }

    @Test
    fun `should get assigned orders`() {
        val assignedOrder1 = Generator.createOrder().copy(status = OrderStatus.ASSIGNED)
        val assignedOrder2 = Generator.createOrder().copy(status = OrderStatus.ASSIGNED)
        orderRepository.upsertOrder(assignedOrder1)
        orderRepository.upsertOrder(assignedOrder2)

        val retrievedOrders = orderRepository.getAssignedOrders()

        assertEquals(listOf(assignedOrder1, assignedOrder2), retrievedOrders)
    }

    @Test
    fun `should return empty list when no assigned orders found`() {
        val createdOrder = Generator.createOrder().copy(status = OrderStatus.CREATED)
        orderRepository.upsertOrder(createdOrder)

        val retrievedOrders = orderRepository.getAssignedOrders()

        assertEquals(emptyList<Order>(), retrievedOrders)
    }

    @Test
    fun `should get created and assigned orders`() {
        val createdOrder1 = Generator.createOrder().copy(status = OrderStatus.CREATED)
        val createdOrder2 = Generator.createOrder().copy(status = OrderStatus.CREATED)
        val assignedOrder1 = Generator.createOrder().copy(status = OrderStatus.ASSIGNED)
        val assignedOrder2 = Generator.createOrder().copy(status = OrderStatus.ASSIGNED)
        orderRepository.upsertOrder(createdOrder1)
        orderRepository.upsertOrder(createdOrder2)
        orderRepository.upsertOrder(assignedOrder1)
        orderRepository.upsertOrder(assignedOrder2)

        val retrievedOrders = orderRepository.getCreatedAndAssignedOrders()

        assertEquals(listOf(createdOrder1, createdOrder2, assignedOrder1, assignedOrder2), retrievedOrders)
    }

    @Test
    fun `should return empty list when no created or assigned orders found`() {
        val deliveredOrder = Generator.createOrder().copy(status = OrderStatus.COMPLETED)
        orderRepository.upsertOrder(deliveredOrder)

        val retrievedOrders = orderRepository.getCreatedAndAssignedOrders()

        assertEquals(emptyList<Order>(), retrievedOrders)
    }
}