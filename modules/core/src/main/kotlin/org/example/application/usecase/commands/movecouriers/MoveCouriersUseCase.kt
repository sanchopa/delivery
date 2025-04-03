package org.example.application.usecase.commands.movecouriers

import org.example.ports.CourierRepository
import org.example.ports.OrderRepository
import org.springframework.transaction.annotation.Transactional

class MoveCouriersUseCase(
    private val courierRepository: CourierRepository,
    private val orderRepository: OrderRepository
) {
    @Transactional
    fun execute() {
        val assignedOrders = orderRepository.getAssignedOrders()
        if (assignedOrders.isEmpty()) {
            return
        }
        for (order in assignedOrders) {
            val courier = order.courierId?.let { courierRepository.getCourier(it) } ?: break
            val updatedCourier = courier.move(order.location)
            if (updatedCourier.location == order.location) {
                courierRepository.upsertCourier(updatedCourier.setStatusFree())
                orderRepository.upsertOrder(order.complete())
                break
            }
            courierRepository.upsertCourier(updatedCourier)
        }
    }
}