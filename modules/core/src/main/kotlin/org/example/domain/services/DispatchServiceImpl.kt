package org.example.domain.services

import org.example.domain.model.courieraggregate.Courier
import org.example.domain.model.courieraggregate.CourierStatus
import org.example.domain.model.orderaggregate.Order
import org.example.domain.model.orderaggregate.OrderStatus

class DispatchServiceImpl : DispatchService {
    override fun dispatch(order: Order, couriers: List<Courier>): Courier {
        if (order.status != OrderStatus.CREATED) {
            throw NoOrdersForDispatchingException("Only orders with status CREATED can be dispatched")
        }

        val freeCouriers = couriers.filter { it.status == CourierStatus.FREE }

        if (freeCouriers.isEmpty()) {
            throw NoFreeCouriersException("No free couriers available to dispatch the order")
        }

        val assignedCourier = freeCouriers.minBy { courier ->
            courier.getStepToTargetLocation(order.location)
        }.setStatusBusy()

        return assignedCourier
    }

    class NoOrdersForDispatchingException(
        val code: String,
        override val message: String
    ) : IllegalArgumentException() {
        constructor(message: String) : this("NO_ORDERS_FOR_DISPATCHING", message)
    }

    class NoFreeCouriersException(
        val code: String,
        override val message: String
    ) : IllegalArgumentException() {
        constructor(message: String) : this("NO_FREE_COURIERS", message)
    }
}