package domain.model.courieraggregate

import domain.model.sharedkernel.Location
import java.util.UUID

data class Courier private constructor(
    val id: UUID,
    val name: String,
    val transport: Transport,
    val location: Location,
    val status: CourierStatus
) {
    companion object {
        fun create(
            name: String,
            nameTransport: String,
            speed: Int,
            location: Location
        ): Courier {
            return Courier(
                UUID.randomUUID(),
                name,
                Transport(UUID.randomUUID(), nameTransport, speed),
                location,
                CourierStatus.FREE
            )
        }
    }

    init {
        require(name.isNotBlank()) { "Name must not be blank" }
    }

    fun setStatusBusy() = this.copy(status = CourierStatus.BUSY)

    fun setStatusFree() = this.copy(status = CourierStatus.FREE)

    fun getStepToTargetLocation(targetLocation: Location): Int {
        val distance = location.distanceTo(targetLocation)
        return distance / transport.speed
    }

    fun move(targetLocation: Location) = this.copy(location = transport.move(location, targetLocation))
}
