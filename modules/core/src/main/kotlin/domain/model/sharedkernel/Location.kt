package domain.model.sharedkernel

import kotlin.math.abs
import kotlin.random.Random

data class Location private constructor(val x: Int, val y: Int) {

    companion object {
        val X_COORDINATE_RANGE = 1..10
        val Y_COORDINATE_RANGE = 1..10

        fun create(x: Int, y: Int): Location {
            return Location(x, y)
        }

        fun createRandom(): Location {
            val randomX = Random.nextInt(1, 11)
            val randomY = Random.nextInt(1, 11)
            return Location(randomX, randomY)
        }
    }

    init {
        require(x in X_COORDINATE_RANGE) { "X coordinate must be between $X_COORDINATE_RANGE" }
        require(y in Y_COORDINATE_RANGE) { "Y coordinate must be between $Y_COORDINATE_RANGE" }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Location) return false

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    fun distanceTo(targetLocation: Location): Int {
        return abs(this.x - targetLocation.x) + abs(this.y - targetLocation.y)
    }
}