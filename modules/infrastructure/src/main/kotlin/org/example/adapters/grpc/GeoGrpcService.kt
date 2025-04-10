package org.example.adapters.grpc

import io.grpc.ManagedChannelBuilder
import org.example.GeoGrpcKt
import org.example.domain.model.sharedkernel.Location
import org.example.getGeolocationRequest
import org.example.ports.GeoService
import org.springframework.stereotype.Service

@Service
class GeoGrpcService : GeoService {
    private val port = System.getenv("PORT")?.toInt() ?: 5004
    private val channel = ManagedChannelBuilder.forAddress("localhost", port).usePlaintext().build()
    private val stub = GeoGrpcKt.GeoCoroutineStub(channel)

    override suspend fun getLocation(street: String): Location {
        val request = getGeolocationRequest { this.street = street }
        val response = stub.getGeolocation(request)
        return Location.create(response.location.x, response.location.y)
    }
}