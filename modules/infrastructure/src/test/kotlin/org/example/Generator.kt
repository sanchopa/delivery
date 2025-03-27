package org.example

import java.util.UUID
import org.example.domain.model.courieraggregate.Courier
import org.example.domain.model.orderaggregate.Order
import org.example.domain.model.sharedkernel.Location

object Generator {
    fun createOrder(
        id: UUID = UUID.randomUUID(),
        location: Location = createRandomLocation()
    ): Order {
        return Order.create(id, location)
    }

    fun createLocation(
        x: Int = 1,
        y: Int = 1
    ): Location {
        return Location.create(x, y)
    }

    fun createRandomLocation(): Location {
        return Location.createRandom()
    }

    fun createCourier(
        name: String = "Courier 1",
        nameTransport: String = "moped",
        speed: Int = 1,
        location: Location = createRandomLocation()
    ): Courier {
        return Courier.create(
            name = name,
            nameTransport = nameTransport,
            speed = speed,
            location = location
        )
    }
}