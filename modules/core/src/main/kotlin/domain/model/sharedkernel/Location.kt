package domain.model.sharedkernel

import kotlin.math.abs
import kotlin.random.Random

data class Location private constructor(val x: Int, val y: Int) {

    companion object {
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
        require(x in 1..10) { "X coordinate must be between 1 and 10" }
        require(y in 1..10) { "Y coordinate must be between 1 and 10" }
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