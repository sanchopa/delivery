package org.example.adapters.postgres.jpa

import java.util.UUID
import org.example.adapters.postgres.entity.CourierEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CourierJpaRepository : JpaRepository<CourierEntity, UUID> {
    fun getByStatus(status: String): List<CourierEntity>
}