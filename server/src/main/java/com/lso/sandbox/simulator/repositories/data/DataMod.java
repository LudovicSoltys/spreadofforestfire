package com.lso.sandbox.simulator.repositories.data;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Module d'activation des "Spring JPA repositories"
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.lso.sandbox.simulator.repositories.data")
public class DataMod {
}
