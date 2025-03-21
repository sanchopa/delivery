package domain.services

import domain.model.courieraggregate.Courier
import domain.model.orderaggregate.Order

interface DispatchService {
    fun dispatch(order: Order, couriers: List<Courier>): Courier
}