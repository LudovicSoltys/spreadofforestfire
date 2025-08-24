package com.lso.sandbox.simulator.fires.propagation.engine;

import com.lso.sandbox.simulator.repositories.data.CellJpaQueryRepository;
import com.lso.sandbox.simulator.repositories.data.DataMod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Configuration du module dédié au calcul de la propagation des incendies
 */
@Configuration
@Import(DataMod.class)
public class FireSpreadingEngineMod {

    @Bean
    public FireSpreadingCalculator propagationProcessor(@Autowired CellJpaQueryRepository cells) {
        return new FireSpreadingCalculatorImpl(cells);
    }
}
