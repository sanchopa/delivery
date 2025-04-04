package org.example.config

import org.example.application.usecase.commands.createorder.CreateOrderUseCase
import org.example.application.usecase.queries.getcouriers.GetCouriersUseCase
import org.example.application.usecase.queries.getorders.GetCreatedAndAssignedOrdersUseCase
import org.example.ports.CourierRepository
import org.example.ports.OrderRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DeliveryUseCaseConfiguration {
    @Bean
    fun getCreateOrderUseCase(
        orderRepository: OrderRepository
    ): CreateOrderUseCase = CreateOrderUseCase(orderRepository)

    @Bean
    fun getGetCouriersUseCase(
        courierRepository: CourierRepository
    ): GetCouriersUseCase = GetCouriersUseCase(courierRepository)

    @Bean
    fun getGetCreatedAndAssignedOrdersUseCase(
        orderRepository: OrderRepository
    ): GetCreatedAndAssignedOrdersUseCase = GetCreatedAndAssignedOrdersUseCase(orderRepository)
}