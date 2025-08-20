package com.lso.sandbox.simulator.infra.engine.di;

import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.FireHandlingProcessor;
import com.lso.sandbox.simulator.infra.data.api.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.infra.data.api.CellJpaCrudRepository;
import com.lso.sandbox.simulator.infra.data.api.CellJpaQueryRepository;
import com.lso.sandbox.simulator.infra.data.di.DataMod;
import com.lso.sandbox.simulator.infra.engine.internal.FireSpreadingCalculator;
import com.lso.sandbox.simulator.infra.engine.internal.HandleFireEngine;
import com.lso.sandbox.simulator.infra.engine.internal.IgniteFireEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DataMod.class)
public class FireSpreadingEngineMod {
    
    @Bean
    public FireHandlingProcessor ignitionProcessor(@Autowired CellJpaQueryRepository query, @Autowired CellJpaCrudRepository cells) {
        return new IgniteFireEngine(query, cells);
    }

    @Bean
    public FireSpreadingCalculator propagationProcessor(@Autowired BoardJpaCrudRepository boards, @Autowired CellJpaQueryRepository cells) {
        return new FireSpreadingCalculator(boards, cells);
    }
    
    @Bean
    public FireHandlingProcessor engineProcessor(@Autowired BoardJpaCrudRepository boards,
                                                 @Autowired CellJpaQueryRepository query,
                                                 @Autowired CellJpaCrudRepository cells,
                                                 @Autowired FireSpreadingCalculator propagationProcessor) {

        return new HandleFireEngine(boards, propagationProcessor, query, cells);
    }
}
