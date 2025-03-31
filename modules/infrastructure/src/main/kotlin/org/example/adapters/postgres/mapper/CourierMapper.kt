package org.example.adapters.postgres.mapper

import org.example.adapters.postgres.entity.CourierEntity
import org.example.adapters.postgres.entity.TransportEntity
import org.example.domain.model.courieraggregate.Courier
import org.example.domain.model.courieraggregate.CourierStatus
import org.example.domain.model.courieraggregate.Transport
import org.example.domain.model.sharedkernel.Location
import org.springframework.stereotype.Component

@Component
class CourierMapper {
    fun toEntity(courier: Courier): CourierEntity {
        return CourierEntity(
            id = courier.id,
            name = courier.name,
            locationX = courier.location.x,
            locationY = courier.location.y,
            status = courier.status.name,
            transport = toEntity(courier.transport)
        )
    }

    fun toDomain(courier: CourierEntity): Courier {
        return Courier(
            id = courier.id,
            name = courier.name,
            transport = toDomain(courier.transport),
            location = Location.create(courier.locationX, courier.locationY),
            status = CourierStatus.valueOf(courier.status)
        )
    }

    fun toEntity(transport: Transport): TransportEntity {
        return TransportEntity(
            id = transport.id,
            name = transport.name,
            speed = transport.speed
        )
    }

    fun toDomain(transport: TransportEntity): Transport {
        return Transport(
            id = transport.id,
            name = transport.name,
            speed = transport.speed
        )
    }
}