package org.example.domain.model.orderaggregate

import java.util.UUID
import org.example.domain.model.sharedkernel.Location

data class Order(
    val id: UUID,
    val location: Location,
    val status: OrderStatus,
    val courierId: UUID? = null
) {
    companion object {
        fun create(id: UUID, location: Location) = Order(id, location, OrderStatus.CREATED)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Order) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    fun assign(courierId: UUID) = copy(status = OrderStatus.ASSIGNED, courierId = courierId)

    fun complete(): Order {
        if (this.status == OrderStatus.ASSIGNED) {
            return copy(status = OrderStatus.COMPLETED)
        } else {
            throw OrderStatusChangeException("Unassigned order with id ${this.id} cannot be completed")
        }
    }

    class OrderStatusChangeException(
        val code: String,
        override val message: String
    ) : RuntimeException() {
        constructor(message: String) : this("ORDER_STATUS_CHANGE_ERROR", message)
    }
}
