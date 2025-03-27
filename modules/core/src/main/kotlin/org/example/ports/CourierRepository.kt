package org.example.ports

import java.util.UUID
import org.example.domain.model.courieraggregate.Courier

interface CourierRepository {
    fun upsertCourier(courier: Courier): Courier
    fun getCourier(courierId: UUID): Courier?
    fun getFreeCouriers(): List<Courier>
}