package org.example.adapters.postgres.jpa

import org.example.adapters.postgres.entity.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface OrderJpaRepository : JpaRepository<OrderEntity, UUID> {
    fun findFirstByStatus(status: String): OrderEntity?
    fun findByStatus(status: String): List<OrderEntity>

    @Query("SELECT oe FROM OrderEntity oe WHERE oe.status IN :statuses")
    fun findByStatus(statuses: List<String>): List<OrderEntity>
}