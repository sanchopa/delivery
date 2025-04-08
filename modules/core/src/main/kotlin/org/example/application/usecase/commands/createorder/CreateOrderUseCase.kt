package org.example.application.usecase.commands.createorder

import kotlinx.coroutines.runBlocking
import org.example.domain.model.orderaggregate.Order
import org.example.ports.GeoService
import org.example.ports.OrderRepository
import org.springframework.transaction.annotation.Transactional

open class CreateOrderUseCase(
    private val orderRepository: OrderRepository,
    private val geoService: GeoService
) {
    @Transactional
    fun execute(createOrderCommand: CreateOrderCommand) {
        if (orderRepository.getOrder(createOrderCommand.basketId) != null) {
            return
        }
        val location = runBlocking {
            geoService.getLocation("Тестировочная")
        }
        val order = Order.create(
            createOrderCommand.basketId,
            location
        )
        orderRepository.upsertOrder(order)
    }
}