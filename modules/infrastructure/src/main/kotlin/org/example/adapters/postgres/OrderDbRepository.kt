package org.example.adapters.postgres

import org.example.adapters.postgres.jpa.OrderJpaRepository
import org.example.adapters.postgres.mapper.OrderMapper
import org.example.domain.model.orderaggregate.Order
import org.example.domain.model.orderaggregate.OrderStatus
import org.example.ports.OrderRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class OrderDbRepository(
    private val orderJpaRepository: OrderJpaRepository,
    private val orderMapper: OrderMapper
) : OrderRepository {
    override fun upsertOrder(order: Order): Order {
        return orderMapper.toDomain(orderJpaRepository.save(orderMapper.toEntity(order)))
    }

    override fun getOrder(orderId: UUID): Order? {
        return orderJpaRepository.findById(orderId).map { orderMapper.toDomain(it) }.orElse(null)
    }

    override fun getCreatedOrder(): Order? {
        return orderJpaRepository.findFirstByStatus(OrderStatus.CREATED.name)?.let { orderMapper.toDomain(it) }
    }

    override fun getAssignedOrders(): List<Order> {
        return orderJpaRepository.findByStatus(OrderStatus.ASSIGNED.name).map { orderMapper.toDomain(it) }
    }

    override fun getCreatedAndAssignedOrders(): List<Order> {
        return orderJpaRepository.findByStatus(listOf(OrderStatus.CREATED.name, OrderStatus.ASSIGNED.name))
            .map { orderMapper.toDomain(it) }
    }
}