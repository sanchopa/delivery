package domain.model.orderaggregate

import domain.model.sharedkernel.Location
import java.util.UUID

data class Order private constructor(
    val id: UUID,
    val location: Location,
    val status: OrderStatus,
    val courierId: UUID? = null
) {
    companion object {
        fun create(id: UUID, location: Location) = Order(id, location, OrderStatus.CREATED)
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
