package org.example.adapters.http.controller

import Api.models.Courier
import Api.models.Error
import Api.models.Location
import Api.models.Order
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.example.application.usecase.commands.createorder.CreateOrderCommand
import org.example.application.usecase.commands.createorder.CreateOrderUseCase
import org.example.application.usecase.queries.getcouriers.GetCouriersUseCase
import org.example.application.usecase.queries.getorders.GetCreatedAndAssignedOrdersUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@Validated
@RequestMapping("delivery")
class ApiApiController(
    private val createOrderUseCase: CreateOrderUseCase,
    private val getCouriersUseCase: GetCouriersUseCase,
    private val getCreatedAndAssignedOrdersUseCase: GetCreatedAndAssignedOrdersUseCase
) {

    @Operation(
        summary = "Создать заказ",
        operationId = "createOrder",
        description = """Позволяет создать заказ с целью тестирования""",
        responses = [
            ApiResponse(responseCode = "201", description = "Успешный ответ"),
            ApiResponse(
                responseCode = "200",
                description = "Ошибка",
                content = [Content(schema = Schema(implementation = Error::class))]
            )]
    )
    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/api/v1/orders"],
        produces = ["application/json"]
    )
    fun createOrder(): ResponseEntity<Unit> {
        val createOrderCommand = CreateOrderCommand(
            basketId = UUID.randomUUID(),
            street = "test"
        )
        return ResponseEntity.ok(createOrderUseCase.execute(createOrderCommand))
    }

    @Operation(
        summary = "Получить всех курьеров",
        operationId = "getCouriers",
        description = """Позволяет получить всех курьеров""",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Успешный ответ",
                content = [Content(array = ArraySchema(schema = Schema(implementation = Courier::class)))]
            ),
            ApiResponse(
                responseCode = "200",
                description = "Ошибка",
                content = [Content(schema = Schema(implementation = Error::class))]
            )]
    )
    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/api/v1/couriers"],
        produces = ["application/json"]
    )
    fun getCouriers(): ResponseEntity<List<Courier>> {
        val getCouriersResponse = getCouriersUseCase.execute()
        val couriers = getCouriersResponse.couriers.map {
            Courier(
                id = it.id,
                name = it.name,
                location = Location(
                    it.location.x,
                    it.location.y
                )
            )
        }
        return ResponseEntity.ok(couriers)
    }

    @Operation(
        summary = "Получить все незавершенные заказы",
        operationId = "getOrders",
        description = """Позволяет получить все незавершенные""",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Успешный ответ",
                content = [Content(array = ArraySchema(schema = Schema(implementation = Order::class)))]
            ),
            ApiResponse(
                responseCode = "200",
                description = "Ошибка",
                content = [Content(schema = Schema(implementation = Error::class))]
            )]
    )
    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/api/v1/orders/active"],
        produces = ["application/json"]
    )
    fun getOrders(): ResponseEntity<List<Order>> {
        val orders = getCreatedAndAssignedOrdersUseCase.execute().orders.map {
            Order(
                id = it.id,
                location = Location(
                    x = it.location.x,
                    y = it.location.y
                )
            )
        }
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }
}
