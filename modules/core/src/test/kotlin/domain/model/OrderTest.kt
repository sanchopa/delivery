package domain.model

import domain.model.orderaggregate.Order
import domain.model.orderaggregate.OrderStatus
import domain.model.sharedkernel.Location
import java.util.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class OrderTest {

    private val testId = UUID.randomUUID()
    private val testLocation = Location.create(5, 5)

    @Test
    fun `should create order with correct initial status`() {
        val order = Order.create(testId, testLocation)
        assertEquals(OrderStatus.CREATED, order.status)
        assertNull(order.courierId)
    }

    @Test
    fun `should assign courier to order and change status to ASSIGNED`() {
        val order = Order.create(testId, testLocation)
        val courierId = UUID.randomUUID()
        val assignedOrder = order.assign(courierId)
        assertEquals(OrderStatus.ASSIGNED, assignedOrder.status)
        assertEquals(courierId, assignedOrder.courierId)
    }

    @Test
    fun `should complete order and change status to COMPLETED`() {
        val order = Order.create(testId, testLocation).assign(UUID.randomUUID())
        val completedOrder = order.complete()
        assertEquals(OrderStatus.COMPLETED, completedOrder.status)
    }

    @Test
    fun `should throw exception when trying to complete unassigned order`() {
        val order = Order.create(testId, testLocation)
        assertThrows(Order.OrderStatusChangeException::class.java) {
            order.complete()
        }
    }

    @Test
    fun `should throw exception with correct message when trying to complete unassigned order`() {
        val order = Order.create(testId, testLocation)
        val exception = assertThrows(Order.OrderStatusChangeException::class.java) {
            order.complete()
        }
        assertEquals("Unassigned order with id ${order.id} cannot be completed", exception.message)
    }
}