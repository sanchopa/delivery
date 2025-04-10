package org.example.ports

import org.example.domain.model.sharedkernel.Location

interface GeoService {
    suspend fun getLocation(street: String): Location
}