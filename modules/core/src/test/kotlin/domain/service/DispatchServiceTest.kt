package domain.service

import domain.model.courieraggregate.Courier
import domain.model.courieraggregate.CourierStatus
import domain.model.orderaggregate.Order
import domain.model.sharedkernel.Location
import domain.services.DispatchServiceImpl
import java.util.UUID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class DispatchServiceImplTest {

    private val dispatchService = DispatchServiceImpl()
    private val targetLocation = Location.create(5, 5)
    private val order = Order.create(UUID.randomUUID(), targetLocation)
    private val courier1 = Courier.create("Courier1", "Bike", 1, Location.create(1, 1))
    private val courier2 = Courier.create("Courier2", "Bike", 2, Location.create(2, 2))

    @Test
    fun testDispatchWithCreatedOrderAndFreeCourier() {
        val assignedCourier = dispatchService.dispatch(order, listOf(courier1))

        assertEquals(CourierStatus.BUSY, assignedCourier.status)
    }

    @Test
    fun testDispatchWithNonCreatedOrderThrowsException() {
        val orderWithCourier = order.assign(UUID.randomUUID())

        assertThrows(DispatchServiceImpl.NoOrdersForDispatchingException::class.java) {
            dispatchService.dispatch(orderWithCourier, listOf(courier1))
        }
    }

    @Test
    fun testDispatchWithNoFreeCouriersThrowsException() {
        val courierBusy = courier1.setStatusBusy()

        assertThrows(DispatchServiceImpl.NoFreeCouriersException::class.java) {
            dispatchService.dispatch(order, listOf(courierBusy))
        }
    }

    @Test
    fun testDispatchWithMultipleFreeCouriersAssignsClosest() {
        val assignedCourier = dispatchService.dispatch(order, listOf(courier1, courier2))

        assertEquals(courier2.id, assignedCourier.id)
    }
}