package org.example.adapters.postgres.mapper

import org.example.adapters.postgres.entity.OrderEntity
import org.example.domain.model.orderaggregate.Order
import org.example.domain.model.orderaggregate.OrderStatus
import org.example.domain.model.sharedkernel.Location
import org.springframework.stereotype.Component

@Component
class OrderMapper {
    fun toEntity(order: Order): OrderEntity {
        return OrderEntity(
            id = order.id,
            locationX = order.location.x,
            locationY = order.location.y,
            status = order.status.name,
            courierId = order.courierId
        )
    }

    fun toDomain(order: OrderEntity): Order {
        return Order(
            id = order.id,
            location = Location.create(order.locationX, order.locationY),
            status = OrderStatus.valueOf(order.status),
            courierId = order.courierId
        )
    }
}