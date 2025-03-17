package domain.model.courieraggregate

import domain.model.sharedkernel.Location
import java.util.UUID
import kotlin.math.abs

data class Transport(
    val id: UUID,
    val name: String,
    val speed: Int
) {
    companion object {
        val TRANSPORT_SPEED_RANGE = 1..3
    }

    init {
        require(speed in TRANSPORT_SPEED_RANGE) { "The speed should be between $TRANSPORT_SPEED_RANGE" }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Transport) return false

        if (id != other.id) return false

        return true
    }

    fun move(current: Location, target: Location): Location {
        val distanceX = target.x - current.x
        val distanceY = target.y - current.y

        val absDistanceX = abs(distanceX)
        val absDistanceY = abs(distanceY)

        val totalDistance = absDistanceX + absDistanceY

        if (totalDistance == 0) {
            return current
        }

        val stepX = if (absDistanceX == 0) 0 else distanceX / absDistanceX
        val stepY = if (absDistanceY == 0) 0 else distanceY / absDistanceY

        val moveDistanceX = minOf(absDistanceX, speed)
        val moveDistanceY = minOf(absDistanceY, speed)

        val newLocationX = current.x + stepX * moveDistanceX
        val newLocationY = current.y + stepY * moveDistanceY

        if (moveDistanceX + moveDistanceY > speed) {
            return if (absDistanceX > absDistanceY) {
                Location.create(newLocationX, current.y)
            } else {
                Location.create(current.x, newLocationY)
            }
        }

        return Location.create(newLocationX, newLocationY)
    }

}