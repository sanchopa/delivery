package org.example.application.usecase.commands.createorder

import org.example.domain.model.orderaggregate.Order
import org.example.domain.model.sharedkernel.Location
import org.example.ports.OrderRepository
import org.springframework.transaction.annotation.Transactional

class CreateOrderUseCase(
    private val orderRepository: OrderRepository
) {
    @Transactional
    fun execute(createOrderCommand: CreateOrderCommand) {
        if (orderRepository.getOrder(createOrderCommand.basketId) != null) {
            return
        }
        val order = Order.create(
            createOrderCommand.basketId,
            Location.createRandom()
        )
        orderRepository.upsertOrder(order)
    }
}