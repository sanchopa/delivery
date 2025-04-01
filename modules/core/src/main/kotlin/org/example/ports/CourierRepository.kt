package org.example.ports

import org.example.domain.model.courieraggregate.Courier
import java.util.UUID

interface CourierRepository {
    fun upsertCourier(courier: Courier): Courier
    fun getCourier(courierId: UUID): Courier?
    fun getFreeCouriers(): List<Courier>
    fun getBusyCouriers(): List<Courier>
}