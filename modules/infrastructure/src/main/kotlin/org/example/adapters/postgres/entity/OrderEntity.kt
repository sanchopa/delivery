package org.example.adapters.postgres.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "orders")
data class OrderEntity(
    @Id
    @Column(name = "id")
    val id: UUID,
    @Column(name = "location_x")
    val locationX: Int,
    @Column(name = "location_y")
    val locationY: Int,
    @Column(name = "status")
    val status: String,
    @Column(name = "courier_id")
    val courierId: UUID?
)