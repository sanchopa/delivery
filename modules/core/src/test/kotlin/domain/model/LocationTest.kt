package domain.model

import domain.model.sharedkernel.Location
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class LocationTest {

    @Test
    fun `should create location with valid coordinates`() {
        val location = Location.create(5, 5)

        assertEquals(5, location.x)
        assertEquals(5, location.y)
    }

    @Test
    fun `should throw IllegalArgumentException for invalid x coordinate`() {
        assertThrows(IllegalArgumentException::class.java) {
            Location.create(0, 5)
        }
        assertThrows(IllegalArgumentException::class.java) {
            Location.create(11, 5)
        }
    }

    @Test
    fun `should throw IllegalArgumentException for invalid y coordinate`() {
        assertThrows(IllegalArgumentException::class.java) {
            Location.create(5, 0)
        }
        assertThrows(IllegalArgumentException::class.java) {
            Location.create(5, 11)
        }
    }

    @Test
    fun `should create random location with coordinates between 1 and 10`() {
        val location = Location.createRandom()

        assertTrue(location.x in 1..10)
        assertTrue(location.y in 1..10)
    }

    @Test
    fun `should calculate correct distance to another location`() {
        val location1 = Location.create(4, 9)
        val location2 = Location.create(2, 6)

        assertEquals(5, location1.distanceTo(location2))
    }

    @Test
    fun `should calculate distance to itself as zero`() {
        val location = Location.create(3, 3)

        assertEquals(0, location.distanceTo(location))
    }

    @Test
    fun `should be equal to another location with the same coordinates`() {
        val location1 = Location.create(3, 3)
        val location2 = Location.create(3, 3)

        assertEquals(location1, location2)
    }

    @Test
    fun `should not be equal to another location with different coordinates`() {
        val location1 = Location.create(3, 3)
        val location2 = Location.create(4, 4)

        assertNotEquals(location1, location2)
    }
}