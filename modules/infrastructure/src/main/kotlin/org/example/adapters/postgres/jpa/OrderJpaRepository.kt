package org.example.adapters.postgres.jpa

import java.util.UUID
import org.example.adapters.postgres.entity.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderJpaRepository : JpaRepository<OrderEntity, UUID> {
    fun findFirstByStatus(status: String): OrderEntity?
    fun findByStatus(status: String): List<OrderEntity>
}