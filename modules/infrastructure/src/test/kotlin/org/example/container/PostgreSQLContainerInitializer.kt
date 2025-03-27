package org.example.container

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container

class PostgreSQLContainerInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    companion object {
        @Container
        val container: PostgreSQLContainer<*> =
            PostgreSQLContainer<Nothing>(
                "postgres:17"
            ).withReuse(true)
    }

    override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
        if (container.isRunning) {
            container.stop()
        }
        container.start()
        TestPropertyValues
            .of("spring.datasource.url=" + container.jdbcUrl)
            .and("spring.datasource.username=" + container.username)
            .and("spring.datasource.password=" + container.password)
            .applyTo(configurableApplicationContext.environment)
    }
}