package org.example.application.usecase.commands.assignorders

import org.example.domain.services.DispatchService
import org.example.ports.CourierRepository
import org.example.ports.OrderRepository
import org.springframework.transaction.annotation.Transactional

class AssignOrdersUseCase(
    private val courierRepository: CourierRepository,
    private val orderRepository: OrderRepository,
    private val dispatchService: DispatchService
) {
    @Transactional
    fun execute() {
        val couriers = courierRepository.getFreeCouriers()
        if (couriers.isEmpty()) return
        val order = orderRepository.getCreatedOrder() ?: return
        val courier = dispatchService.dispatch(order, couriers)
        courierRepository.upsertCourier(courier)
        orderRepository.upsertOrder(order.assign(courier.id))
    }
}