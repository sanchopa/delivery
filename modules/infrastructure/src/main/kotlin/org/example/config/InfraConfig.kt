package org.example.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories("org.example.adapters.postgres.*")
@EntityScan("org.example.adapters.postgres.*")
class InfraConfig