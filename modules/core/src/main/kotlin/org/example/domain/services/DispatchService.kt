package org.example.domain.services

import org.example.domain.model.courieraggregate.Courier
import org.example.domain.model.orderaggregate.Order

interface DispatchService {
    fun dispatch(order: Order, couriers: List<Courier>): Courier
}