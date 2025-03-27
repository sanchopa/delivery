package org.example.adapters.postgres.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "couriers")
data class CourierEntity(
    @Id
    @Column(name = "id")
    val id: UUID,
    @Column(name = "name")
    val name: String,
    @Column(name = "location_x")
    val locationX: Int,
    @Column(name = "location_y")
    val locationY: Int,
    @Column(name = "status")
    val status: String,
    @OneToOne
    @JoinColumn(name = "transport_id", referencedColumnName = "id")
    val transport: TransportEntity,
)