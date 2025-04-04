package org.example.application.usecase.queries.getorders

import org.example.domain.model.orderaggregate.Order
import org.example.ports.OrderRepository

open class GetCreatedAndAssignedOrdersUseCase(
    private val orderRepository: OrderRepository
) {
    fun execute(): GetCreatedAndAssignedOrdersResponse {
        val orders = orderRepository.getCreatedAndAssignedOrders()
        return mapToResponse(orders)
    }

    private fun mapToResponse(orders: List<Order>): GetCreatedAndAssignedOrdersResponse {
        val orderResponses = orders.map { order ->
            GetCreatedAndAssignedOrdersResponse.Order(
                id = order.id,
                location = GetCreatedAndAssignedOrdersResponse.Order.Location(
                    x = order.location.x,
                    y = order.location.y
                )
            )
        }
        return GetCreatedAndAssignedOrdersResponse(orderResponses)
    }
}