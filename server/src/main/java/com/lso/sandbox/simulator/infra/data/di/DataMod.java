package com.lso.sandbox.simulator.infra.data.di;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.lso.sandbox.simulator.infra.data.api")
public class DataMod {
}
