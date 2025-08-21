package com.lso.sandbox.simulator.fires.propagation.engine;

import com.lso.sandbox.simulator.fires.propagation.calcul.FireSpreadingCalculatorImpl;
import com.lso.sandbox.simulator.repositories.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.repositories.CellJpaCrudRepository;
import com.lso.sandbox.simulator.repositories.CellJpaQueryRepository;
import com.lso.sandbox.simulator.repositories.DataMod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DataMod.class)
public class FireSpreadingEngineMod {

    @Bean
    public FireSpreadingCalculatorImpl propagationProcessor(@Autowired BoardJpaCrudRepository boards, @Autowired CellJpaQueryRepository cells) {
        return new FireSpreadingCalculatorImpl(boards, cells);
    }
    
    @Bean
    public FireSpreadingProcessor engineProcessor(@Autowired BoardJpaCrudRepository boards,
                                                  @Autowired CellJpaQueryRepository query,
                                                  @Autowired CellJpaCrudRepository cells,
                                                  @Autowired FireSpreadingCalculatorImpl propagationProcessor) {

        return new FireSpreadingEngine(boards, propagationProcessor, query, cells);
    }
}
