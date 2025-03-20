package domain.model

import domain.model.courieraggregate.Courier
import domain.model.courieraggregate.CourierStatus
import domain.model.sharedkernel.Location
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CourierTest {

    @Test
    fun `should create a courier with correct initial values`() {
        val name = "John Doe"
        val transportName = "Bike"
        val speed = 2
        val location = Location.create(1, 1)

        val courier = Courier.create(name, transportName, speed, location)

        assertNotNull(courier.id)
        assertEquals(name, courier.name)
        assertEquals(transportName, courier.transport.name)
        assertEquals(speed, courier.transport.speed)
        assertEquals(location, courier.location)
        assertEquals(CourierStatus.FREE, courier.status)
    }

    @Test
    fun `should set status to busy`() {
        val courier = Courier.create("John Doe", "Bike", 2, Location.create(1, 1))
        val busyCourier = courier.setStatusBusy()

        assertEquals(CourierStatus.BUSY, busyCourier.status)
    }

    @Test
    fun `should set status to free`() {
        val courier = Courier.create("John Doe", "Bike", 2, Location.create(1, 1))
        val busyCourier = courier.setStatusBusy()
        val freeCourier = busyCourier.setStatusFree()

        assertEquals(CourierStatus.FREE, freeCourier.status)
    }

    @Test
    fun `should calculate correct steps to target location`() {
        val courier = Courier.create("John Doe", "Bike", 2, Location.create(1, 1))
        val targetLocation = Location.create(3, 3)

        val steps = courier.getStepToTargetLocation(targetLocation)

        assertEquals(2, steps)
    }

    @Test
    fun `should move to target location correctly`() {
        val courier = Courier.create("John Doe", "Bike", 2, Location.create(1, 1))
        val targetLocation = Location.create(3, 3)

        val movedCourier = courier.move(targetLocation)

        assertEquals(Location.create(1, 3), movedCourier.location)
    }
}