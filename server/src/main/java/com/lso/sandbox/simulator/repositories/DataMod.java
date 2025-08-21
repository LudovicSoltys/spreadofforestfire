package com.lso.sandbox.simulator.repositories;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.lso.sandbox.simulator.repositories")
public class DataMod {
}
