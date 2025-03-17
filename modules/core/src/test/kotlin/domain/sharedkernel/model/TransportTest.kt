package domain.sharedkernel.model

import domain.model.courieraggregate.Transport
import domain.model.sharedkernel.Location
import java.util.UUID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TransportTest {
    private val transportId = UUID.randomUUID()
    private val transportName = "Test Transport"

    @Test
    fun `move should return the same location if current and target are the same`() {
        val currentLocation = Location.create(1, 1)
        val targetLocation = Location.create(1, 1)
        val transport = Transport(transportId, transportName, 2)

        val newLocation = transport.move(currentLocation, targetLocation)

        assertEquals(currentLocation, newLocation)
    }

    @Test
    fun `move should move horizontally to the right within speed limit`() {
        val currentLocation = Location.create(1, 1)
        val targetLocation = Location.create(3, 1)
        val transport = Transport(transportId, transportName, 2)

        val newLocation = transport.move(currentLocation, targetLocation)

        assertEquals(Location.create(3, 1), newLocation)
    }

    @Test
    fun `move should move horizontally to the left within speed limit`() {
        val currentLocation = Location.create(3, 1)
        val targetLocation = Location.create(1, 1)
        val transport = Transport(transportId, transportName, 2)

        val newLocation = transport.move(currentLocation, targetLocation)

        assertEquals(Location.create(1, 1), newLocation)
    }

    @Test
    fun `move should move vertically upwards within speed limit`() {
        val currentLocation = Location.create(1, 1)
        val targetLocation = Location.create(1, 3)
        val transport = Transport(transportId, transportName, 2)

        val newLocation = transport.move(currentLocation, targetLocation)

        assertEquals(Location.create(1, 3), newLocation)
    }

    @Test
    fun `move should move vertically downwards within speed limit`() {
        val currentLocation = Location.create(1, 3)
        val targetLocation = Location.create(1, 1)
        val transport = Transport(transportId, transportName, 2)

        val newLocation = transport.move(currentLocation, targetLocation)

        assertEquals(Location.create(1, 1), newLocation)
    }

    @Test
    fun `move should not overshoot the target location`() {
        val currentLocation = Location.create(1, 1)
        val targetLocation = Location.create(2, 2)
        val transport = Transport(transportId, transportName, 3)

        val newLocation = transport.move(currentLocation, targetLocation)

        assertEquals(targetLocation, newLocation)
    }

    @Test
    fun `move should handle speed less than distance`() {
        val currentLocation = Location.create(1, 1)
        val targetLocation = Location.create(3, 3)
        val transport = Transport(transportId, transportName, 1)

        val newLocation = transport.move(currentLocation, targetLocation)

        assertEquals(Location.create(1, 2), newLocation)
    }

    @Test
    fun `move should handle speed greater than distance`() {
        val currentLocation = Location.create(1, 1)
        val targetLocation = Location.create(2, 2)
        val transport = Transport(transportId, transportName, 3)

        val newLocation = transport.move(currentLocation, targetLocation)

        assertEquals(Location.create(2, 2), newLocation)
    }
}