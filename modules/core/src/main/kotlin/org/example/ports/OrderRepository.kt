package org.example.ports

import org.example.domain.model.orderaggregate.Order
import java.util.UUID

interface OrderRepository {
    fun upsertOrder(order: Order): Order
    fun getOrder(orderId: UUID): Order?
    fun getCreatedOrder(): Order?
    fun getAssignedOrders(): List<Order>
    fun getCreatedAndAssignedOrders(): List<Order>
}