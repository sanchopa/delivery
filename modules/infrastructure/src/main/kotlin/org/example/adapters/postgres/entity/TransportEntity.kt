package org.example.adapters.postgres.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "transports")
data class TransportEntity(
    @Id
    @Column(name = "id")
    val id: UUID,
    @Column(name = "name")
    val name: String,
    @Column(name = "speed")
    val speed: Int
)
