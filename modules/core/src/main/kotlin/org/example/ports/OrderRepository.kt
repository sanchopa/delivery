package org.example.ports

import java.util.UUID
import org.example.domain.model.orderaggregate.Order

interface OrderRepository {
    fun upsertOrder(order: Order): Order
    fun getOrder(orderId: UUID): Order?
    fun getCreatedOrder(): Order?
    fun getAssignedOrders(): List<Order>
}