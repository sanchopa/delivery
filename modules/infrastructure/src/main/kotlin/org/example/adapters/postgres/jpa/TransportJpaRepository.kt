package org.example.adapters.postgres.jpa

import java.util.UUID
import org.example.adapters.postgres.entity.TransportEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TransportJpaRepository : JpaRepository<TransportEntity, UUID>