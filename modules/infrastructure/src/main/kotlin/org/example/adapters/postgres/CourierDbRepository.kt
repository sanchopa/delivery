package org.example.adapters.postgres

import org.example.adapters.postgres.jpa.CourierJpaRepository
import org.example.adapters.postgres.jpa.TransportJpaRepository
import org.example.adapters.postgres.mapper.CourierMapper
import org.example.domain.model.courieraggregate.Courier
import org.example.domain.model.courieraggregate.CourierStatus
import org.example.ports.CourierRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class CourierDbRepository(
    private val courierJpaRepository: CourierJpaRepository,
    private val transportJpaRepository: TransportJpaRepository,
    private val courierMapper: CourierMapper
) : CourierRepository {
    @Transactional
    override fun upsertCourier(courier: Courier): Courier {
        val courierEntity = courierMapper.toEntity(courier)
        transportJpaRepository.save(courierEntity.transport)
        return courierMapper.toDomain(courierJpaRepository.save(courierEntity))
    }

    override fun getCourier(courierId: UUID): Courier? {
        return courierJpaRepository.findById(courierId).map { courierMapper.toDomain(it) }.orElse(null)
    }

    override fun getFreeCouriers(): List<Courier> {
        return courierJpaRepository.getByStatus(CourierStatus.FREE.name).map { courierMapper.toDomain(it) }
    }

    override fun getBusyCouriers(): List<Courier> {
        return courierJpaRepository.getByStatus(CourierStatus.BUSY.name).map { courierMapper.toDomain(it) }
    }
}