package org.example.postgres

import org.example.Generator
import org.example.TestConfig
import org.example.adapters.postgres.CourierDbRepository
import org.example.adapters.postgres.jpa.CourierJpaRepository
import org.example.adapters.postgres.mapper.CourierMapper
import org.example.config.InfraConfig
import org.example.container.PostgreSQLContainerInitializer
import org.example.domain.model.courieraggregate.Courier
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import java.util.UUID

@ContextConfiguration(
    classes = [TestConfig::class,
        InfraConfig::class,
        CourierJpaRepository::class,
        CourierDbRepository::class,
        CourierMapper::class],
    initializers = [PostgreSQLContainerInitializer::class]
)
class CourierDbRepositoryTest : AbstractRepositoryTest() {
    @Autowired
    lateinit var courierRepository: CourierDbRepository

    @Test
    fun `should save courier`() {
        val courier = Generator.createCourier()

        val res = courierRepository.upsertCourier(courier)

        assertEquals(res, courier)
    }

    @Test
    fun `should get courier by id`() {
        val courier = Generator.createCourier()
        courierRepository.upsertCourier(courier)

        val retrievedCourier = courierRepository.getCourier(courier.id)

        assertEquals(courier, retrievedCourier)
    }

    @Test
    fun `should return null when courier not found by id`() {
        val nonExistentCourierId = UUID.randomUUID()

        val retrievedCourier = courierRepository.getCourier(nonExistentCourierId)

        assertNull(retrievedCourier)
    }

    @Test
    fun `should get free couriers`() {
        val freeCourier1 = Generator.createCourier().setStatusFree()
        val freeCourier2 = Generator.createCourier().setStatusFree()
        courierRepository.upsertCourier(freeCourier1)
        courierRepository.upsertCourier(freeCourier2)

        val retrievedCouriers = courierRepository.getFreeCouriers()

        assertEquals(listOf(freeCourier1, freeCourier2), retrievedCouriers)
    }

    @Test
    fun `should return empty list when no free couriers found`() {
        val busyCourier = Generator.createCourier().setStatusBusy()
        courierRepository.upsertCourier(busyCourier)

        val retrievedCouriers = courierRepository.getFreeCouriers()

        assertEquals(emptyList<Courier>(), retrievedCouriers)
    }

    @Test
    fun `should get busy couriers`() {
        val busyCourier1 = Generator.createCourier().setStatusBusy()
        val busyCourier2 = Generator.createCourier().setStatusBusy()
        courierRepository.upsertCourier(busyCourier1)
        courierRepository.upsertCourier(busyCourier2)

        val retrievedCouriers = courierRepository.getBusyCouriers()

        assertEquals(listOf(busyCourier1, busyCourier2), retrievedCouriers)
    }

    @Test
    fun `should return empty list when no busy couriers found`() {
        val freeCourier = Generator.createCourier().setStatusFree()
        courierRepository.upsertCourier(freeCourier)

        val retrievedCouriers = courierRepository.getBusyCouriers()

        assertEquals(emptyList<Courier>(), retrievedCouriers)
    }
}